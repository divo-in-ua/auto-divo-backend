package divo.auto.configurations

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "api")
data class ApiExampleConfiguration(
    val clientId: String,
    val url: String,
    val key: String
)