package divo.auto.configurations

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.data.mongodb")
data class MongodbConfiguration (
    val host: String,
    val port: Int,
    val database: String,
    val userName: String,
    val password: String
)