package divo.auto.utils.logs

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import divo.auto.entities.DivoLogEntity
import kotlinx.coroutines.*
import org.bson.Document
import java.io.FileInputStream
import java.io.IOException
import java.util.*

private const val DATABASE_PROP_NAME = "spring.data.mongodb.database"
private const val DATABASE_PROP_HOST = "spring.data.mongodb.host"
private const val DATABASE_PROP_PORT = "spring.data.mongodb.port"
private const val DATABASE_PROP_USER_NAME = "spring.data.mongodb.userName"
private const val DATABASE_PROP_USER_PASSWORD = "spring.data.mongodb.password"
private const val COLLECTION_NAME = "springLogs"

/**
 * The `DivoLogAppender` class is a Logback appender that logs events to a MongoDB collection.
 * It handles database initialization, event logging, and property loading from multiple paths.
 */
class DivoLogAppender: AppenderBase<ILoggingEvent>() {
    @Volatile private var collection: MongoCollection<Document>? = null
    private val propertiesList = loadProperties()

    companion object {
        /**
         * Loads properties from multiple configuration file paths.
         */
        private fun loadProperties(): List<Properties> {
            val paths = arrayListOf(
                "./config/application-production.properties",
                "./config/application-development.properties",
                "/app/config/application-development.properties",
                "/app/config/application-production.properties",
            )
            return paths.map { loadPropertiesFromFile(it) }
        }

        /**
         * Loads properties from a file.
         */
        private fun loadPropertiesFromFile(path: String): Properties {
            val properties = Properties()
            try {
                FileInputStream(path).use { inputStream -> properties.load(inputStream) }
            } catch (ex: IOException) {
                ex.message
            }
            return properties
        }

        fun getCollectionName(): String {
            return COLLECTION_NAME
        }
    }

    /**
     * Appends the log event to the MongoDB collection.
     */
    override fun append(event: ILoggingEvent?) {
        if (event == null) return

        if (collection == null) {
            collection = initCollection()
        }

        CoroutineScope(Dispatchers.IO).launch {
            runCatching {
                saveEvent(event)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    fun saveEvent(event: ILoggingEvent) {
        val logData: Document = DivoLogEntity(
            level = event.level.toString(),
            message = event.formattedMessage,
            createdAt = Date(event.timeStamp),
            loggerName = event.loggerName,
        ).convertToDocument()
        collection?.insertOne(logData)
    }

    /**
     * Initializes the database and the MongoDB client if not already created.
     */
    fun initCollection(): MongoCollection<Document> {
        val mongoClient = createMongoClient()
        val database = mongoClient.getDatabase(getProperty(DATABASE_PROP_NAME))
        return database.getCollection(COLLECTION_NAME)
    }

    /**
     * Creates and configures the MongoDB client.
     */
    private fun createMongoClient(): MongoClient {
        val port = Integer.parseInt(getProperty(DATABASE_PROP_PORT))
        val serverAddress = ServerAddress(getProperty(DATABASE_PROP_HOST), port)
        val databaseName = getProperty(DATABASE_PROP_NAME)
        val databaseUser = getProperty(DATABASE_PROP_USER_NAME)
        val databaseUserPassword = getProperty(DATABASE_PROP_USER_PASSWORD)

        val mongoCredential = MongoCredential.createCredential(
            databaseUser,
            databaseName,
            databaseUserPassword.toCharArray()
        )

        val settings = MongoClientSettings.builder()
            .credential(mongoCredential)
            .applyToSslSettings { it.enabled(false) }
            .applyToClusterSettings { it.hosts(listOf(serverAddress)) }
            .build()

        return MongoClients.create(settings)
    }

    /**
     * Retrieves a property from the loaded properties list.
     *
     * @param key The property key to retrieve.
     * @return The property value, or an empty string if the property is not found.
     */
    private fun getProperty(key: String): String {
        var result = "" // default value
        propertiesList.forEach {
            val propertyValue = it.getProperty(key, "")
            if (!propertyValue.isNullOrBlank()) {
                result = propertyValue
            }
        }
        return result
    }
}