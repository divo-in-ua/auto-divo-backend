package divo.auto.controllers

import divo.auto.configurations.ApiExampleConfiguration
import divo.auto.configurations.DivoMongodbConfiguration
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api")
class DivoApiController(
    val apiConfiguration: ApiExampleConfiguration,
    val mongodbConfiguration: DivoMongodbConfiguration
) {

    @GetMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
    fun index(): ResponseEntity<String> {
        val response = ResponseEntity("{\"result\":\"ok\"}", HttpStatus.OK)
        return response
    }

    @PostMapping("/login", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(): ResponseEntity<String> {
        val response = ResponseEntity("{\"result LOGIN\":\"ok\"}", HttpStatus.CREATED)
        return response
    }
}