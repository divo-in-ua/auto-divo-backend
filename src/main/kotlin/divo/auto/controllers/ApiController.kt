package divo.auto.controllers

import divo.auto.ApiConfiguration
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController(val apiConfiguration: ApiConfiguration) {

    @GetMapping("/api")
    fun index(): String {
        return "Hello, World apiConfiguration ${apiConfiguration.clientId} ${apiConfiguration.key} ${apiConfiguration.url}"
    }
}