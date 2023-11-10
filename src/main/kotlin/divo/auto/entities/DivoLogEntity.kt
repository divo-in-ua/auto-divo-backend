package divo.auto.entities

import divo.auto.domain.DivoLog
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.Date

/**
 * The class represents a log entry entity that can be stored in a MongoDB collection.
 * It implements the `Log` interface and is suitable for database-related operations.
 */
@Document(collection = "logs")
data class DivoLogEntity(
    @Id
    override var id: String? = null,
    override var loggerName: String? = null,
    override var level: String? = null,
    override var message: String? = null,
    @CreatedDate
    override var createdAt: Date? = null,
): DivoLog {
    /** Uses for directly insert into MongoCollections */
    fun convertToDocument(): org.bson.Document {
        val document = org.bson.Document()
        if (id != null) {
            document.append("id", id)
        }
        if (loggerName != null) {
            document.append("loggerName", loggerName)
        }
        if (level != null) {
            document.append("level", level)
        }
        if (message != null) {
            document.append("message", message)
        }
        if (createdAt != null) {
            document.append("createdAt", createdAt)
        }
        return document
    }
}