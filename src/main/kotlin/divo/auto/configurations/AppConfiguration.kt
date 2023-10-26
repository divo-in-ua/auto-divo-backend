package divo.auto.configurations

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ApiConfiguration::class, MongodbConfiguration::class)
class AppConfiguration