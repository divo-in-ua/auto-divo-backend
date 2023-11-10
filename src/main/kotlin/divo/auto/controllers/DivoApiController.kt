package divo.auto.controllers

import divo.auto.configurations.ApiExampleConfiguration
import divo.auto.configurations.DivoMongodbConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class DivoApiController(
    val apiConfiguration: ApiExampleConfiguration,
    val mongodbConfiguration: DivoMongodbConfiguration
) {

    @GetMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun index(): ResponseEntity<String> {
//        if (true) return ResponseEntity("Test Not Found Result", HttpStatus.NOT_FOUND)
        return ResponseEntity("{\"result\":\"ok\"}", HttpStatus.OK)
    }
}