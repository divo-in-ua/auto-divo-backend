package divo.auto.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ApiController {
    @GetMapping("/api")
    fun index(): String {
        return "Hello, World"
    }
}