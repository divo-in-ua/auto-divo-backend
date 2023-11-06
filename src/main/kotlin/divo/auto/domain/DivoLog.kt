package divo.auto.domain

import java.util.*

/**
 * The `Log` interface represents a log entry in a logging system.
 * It extends the `DomainModel` interface, indicating that log entries are domain models.
 * This interface defines properties commonly associated with log entries.
 */
interface DivoLog: DivoDomainModel {
    var id: String?
    var level: String?
    var message: String?
    var createdAt: Date?
}