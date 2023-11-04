package divo.auto.controllers

import divo.auto.configurations.ApiExampleConfiguration
import divo.auto.configurations.DivoMongodbConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DivoApiController(
    val apiConfiguration: ApiExampleConfiguration,
    val mongodbConfiguration: DivoMongodbConfiguration
) {

    @GetMapping("/api")
    fun index(): String {
        return "Hello, World!"
    }
}