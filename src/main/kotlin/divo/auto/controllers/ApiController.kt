package divo.auto.controllers

import divo.auto.configurations.ApiConfiguration
import divo.auto.configurations.MongodbConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController(val apiConfiguration: ApiConfiguration, val mongodbConfiguration: MongodbConfiguration) {

    @GetMapping("/api")
    fun index(): String {
        return "Hello, World apiConfiguration " +
                "${apiConfiguration.clientId} ${apiConfiguration.key} ${apiConfiguration.url}" +
                ", mongodbConfiguration: ${mongodbConfiguration.password}"
    }
}