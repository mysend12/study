package com.infosung.pushservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class PushServiceApplication

fun main(args: Array<String>) {
    runApplication<PushServiceApplication>(*args)
}
