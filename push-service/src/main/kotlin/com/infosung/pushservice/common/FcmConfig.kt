package com.infosung.pushservice.common

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.infosung.pushservice.push.FcmProperties
import com.infosung.pushservice.push.ServiceName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.FileInputStream

data class FirebasePack(
    val firebaseApp: FirebaseApp,
    val firebaseAuth: FirebaseAuth,
    val firebaseMessaging: FirebaseMessaging,
)

@Configuration
class FcmConfig @Autowired constructor(
    private val properties: FcmProperties,
) {

    @Bean
    fun firebasePacks(): Map<ServiceName, FirebasePack> = properties.projects.keys.associate { key ->
        val serviceName = ServiceName.valueOf(key)
        val firebaseApp = this.firebaseApp(serviceName)
        serviceName to FirebasePack(
            firebaseApp = firebaseApp,
            firebaseAuth = firebaseAuth(firebaseApp),
            firebaseMessaging = firebaseMessaging(firebaseApp),
        )
    }

    private fun firebaseAuth(firebaseApp: FirebaseApp): FirebaseAuth =
        FirebaseAuth.getInstance(firebaseApp)

    private fun firebaseMessaging(firebaseApp: FirebaseApp): FirebaseMessaging =
        FirebaseMessaging.getInstance(firebaseApp)

    private fun firebaseApp(serviceName: ServiceName): FirebaseApp = FirebaseApp.initializeApp(
        FirebaseOptions.builder()
            .setCredentials(
                this.googleCredentials(serviceName)
            ).build(),
        serviceName.name
    )

    private fun googleCredentials(serviceName: ServiceName): GoogleCredentials {
        val credentials = GoogleCredentials
            .fromStream(
                FileInputStream(
                    ClassLoader.getSystemResource(serviceName.sdkName())
                        .file
                )
            ).createScoped(properties.scopes)
        credentials.refreshAccessToken()
        return credentials
    }

    private fun ServiceName.sdkName() = when (this) {
        ServiceName.TEST -> properties.projects[ServiceName.TEST.name]?.sdkName
            ?: throw Exception("project property is wrong")
    }
}

