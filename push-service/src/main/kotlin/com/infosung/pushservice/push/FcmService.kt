package com.infosung.pushservice.push

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.core.ApiFuture
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.infosung.pushservice.common.FirebasePack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

/**
 * https://firebase.google.com/docs/reference/fcm/rest/v1/projects.messages?hl=ko#Notification
 */
@Service
class FcmService @Autowired constructor(
    private val objectMapper: ObjectMapper,
    private val firebasePacks: Map<ServiceName, FirebasePack>,
) {
    private final val log = LoggerFactory.getLogger(this::class.java)

    private suspend fun sendMessage(
        serviceName: ServiceName,
        targetGroupType: TargetGroupType,
        target: List<String>,
        title: String,
        body: Any,
        datas: Map<String, String>? = null,
        imageUrl: String? = null,
    ) {
        log.debug(
            """
            sendMessage is start! 
            serviceName: {}
            targetGroupType: {}
            target: {}
            title: {}
            body: {}
            datas: {}
            imageUrl: {}
        """.trimIndent(),
            serviceName, targetGroupType, target, title, body, datas, imageUrl
        )

        if (target.isEmpty()) throw Exception("target은 비어있을 수 없습니다.")

        val firebaseMessage = firebasePacks[serviceName]?.firebaseMessaging!!
        val bodyJson = if (body !is String) {
            objectMapper.writeValueAsString(body)
        } else body

        var messageBuilder = Message.builder()
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(bodyJson)
                    .setImage(imageUrl)
                    .build()
            )

        messageBuilder = setTarget(
            targetGroupType = targetGroupType,
            messageBuilder = messageBuilder,
            target = target
        )

        datas?.forEach { (key, value) -> messageBuilder = messageBuilder.putData(key, value) }
        val sendAsync = firebaseMessage.sendAsync(
            messageBuilder.build()
        )
        val result = sendFcm(sendAsync)
        log.debug("result: {}", result)

        // TODO result 결과에 따라서, 데이터베이스에 결과 Insert
    }

    private fun setTarget(
        targetGroupType: TargetGroupType,
        messageBuilder: Message.Builder,
        target: List<String>,
    ): Message.Builder {
        log.debug("sendTarget is called")
        var builder = messageBuilder
        when (targetGroupType) {
            TargetGroupType.PERSONAL -> {
                builder = builder.setToken(target.first())
            }

            TargetGroupType.TOPIC -> {
                builder = builder.setTopic(target.first())
            }

            TargetGroupType.CONDITION -> {
                target.forEach { condition -> builder = builder.setCondition(condition) }
            }
        }
        return builder
    }

    private suspend fun sendFcm(sendAsync: ApiFuture<String>): Boolean = withContext(Dispatchers.IO) {
        log.debug("sendFcm is called")
        try {
            val message = sendAsync.get(1L, TimeUnit.SECONDS)
            log.info("message: {}", message)
            true
        } catch (e: Exception) {
            log.error("FCM Push Error", e)
            false
        }
    }


}