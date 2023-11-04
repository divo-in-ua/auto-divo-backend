package divo.auto.configurations

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing

@Configuration
@EnableConfigurationProperties(
    ApiExampleConfiguration::class,
    DivoMongodbConfiguration::class,
)
@EnableMongoAuditing
class DivoApplicationConfiguration