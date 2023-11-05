package divo.auto.utils.logs

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.AppenderBase
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bson.Document
import java.io.FileInputStream
import java.io.IOException
import java.util.*

private const val PRODUCTION_PROP = "./config/application-production.properties"
private const val DEVELOPMENT_PROP = "./config/application-development.properties"
private const val DOCKER_DEVELOPMENT_PROP = "/app/config/application-development.properties"
private const val DOCKER_PRODUCTION_PROP = "/app/config/application-production.properties"
private const val DATABASE_PROP_NAME = "spring.data.mongodb.database"
private const val DATABASE_PROP_HOST = "spring.data.mongodb.host"
private const val DATABASE_PROP_PORT = "spring.data.mongodb.port"
private const val DATABASE_PROP_USER_NAME = "spring.data.mongodb.userName"
private const val DATABASE_PROP_USER_PASSWORD = "spring.data.mongodb.password"
private const val COLLECTION_NAME = "springLogs"
class DivoLogAppender: AppenderBase<ILoggingEvent>() {
    @Volatile private var mongoClient: MongoClient? = null
    @Volatile private var collection: MongoCollection<Document>? = null
    private val propertiesProduction: Properties = loadPropertiesFromFile(PRODUCTION_PROP)
    private val propertiesDevelopment: Properties = loadPropertiesFromFile(DEVELOPMENT_PROP)
    private val propertiesDevDocker: Properties = loadPropertiesFromFile(DOCKER_DEVELOPMENT_PROP)
    private val propertiesProdDocker: Properties = loadPropertiesFromFile(DOCKER_PRODUCTION_PROP)

    companion object {
        @JvmStatic
        private fun loadPropertiesFromFile(path: String): Properties {
            val properties = Properties()
            try {
                FileInputStream(path).use { inputStream -> properties.load(inputStream) }
            } catch (_: IOException) { }
            return properties
        }
    }
    override fun append(event: ILoggingEvent?) {
        initDatabaseIfStillNotCreated()
        CoroutineScope(Dispatchers.IO).launch {
            val logData = Document()
            logData.append("level", event?.level.toString())
            logData.append("message", event?.formattedMessage)
            logData.append("createdAt", Date(event?.timeStamp ?: 0))
            runCatching {
                collection?.insertOne(logData)
            }.onFailure {
                it.printStackTrace()
            }
        }
    }

    private fun initDatabaseIfStillNotCreated() {
        if (mongoClient == null) {
            runBlocking {
                mongoClient = createMongoClient()
                val database = mongoClient!!.getDatabase(getProperty(DATABASE_PROP_NAME))
                collection = database.getCollection(COLLECTION_NAME)
            }
        }
    }

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

    private fun getProperty(key: String): String {
        return try {
            val propertyDevDocker = propertiesDevDocker.getProperty(key, "")
            val devProp = propertiesDevelopment.getProperty(key, propertyDevDocker)
            val propertyProdDocker = propertiesProdDocker.getProperty(key, devProp)
            propertiesProduction.getProperty(key, propertyProdDocker)
        } catch (e: Throwable) {
            e.printStackTrace()
            ""
        }
    }
}