package divo.auto.configurations

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.mongodb")
data class DivoMongodbConfiguration (
    val host: String,
    val port: Int,
    val database: String,
    val userName: String,
    val password: String
)