package com.infosung.pushservice.push

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "fcm")
data class FcmProperties(
    val scopes: List<String>,
    val projects: Map<String, FcmProject>,
) {
    data class FcmProject(
        val projectId: String,
        val sdkName: String,
    )
}

enum class ServiceName {
    TEST,
}