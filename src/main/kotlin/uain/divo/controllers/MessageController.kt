package uain.divo.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MessageController {
    @GetMapping("/")
    fun index(@RequestParam("name") paramName: String?): String {
        val name = paramName ?: "Guest"
        return "Hello, $name!"
    }
}