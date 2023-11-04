package divo.auto.entities

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

@Document(collection = "logs")
data class DivoLogEntity(
    @Id
    val id: String? = null,
    val level: String? = null,
    val message: String? = null,
    @CreatedDate
    val createdAt: Date? = null,
)