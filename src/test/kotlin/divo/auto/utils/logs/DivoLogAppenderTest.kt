package divo.auto.utils.logs

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.spi.ILoggingEvent
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.mockito.*
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class DivoLogAppenderTest {
    @InjectMocks
    private lateinit var appender: DivoLogAppender

    @Mock
    private lateinit var collection: MongoCollection<Document>

    @Captor
    private lateinit var documentCaptor: ArgumentCaptor<Document>

    private val testMessage = "Log message"

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this@DivoLogAppenderTest)
    }

    @Test
    fun testAppend() {
        val logEvent: ILoggingEvent = createMockLoggingEvent(timeStamp = System.currentTimeMillis())
        // Call the function to save the log event
        appender.saveEvent(logEvent)
        // Verify that the insertOne method is called on the collection with the captured Document
        verify(collection).insertOne(documentCaptor.capture())
        // Retrieve the captured Document and verify its content
        val capturedDocument = documentCaptor.value
        assertEquals(Level.INFO.levelStr, capturedDocument.getString("level"))
        assertEquals(testMessage, capturedDocument.getString("message"))
        assertTrue(capturedDocument.getLong("createdAt") > 0)
    }

    @Test
    fun testInitCollection() {
        // Verify that the MongoDB client and collection are correctly initialized
        val collection = appender.initCollection()
        assertNotNull(collection)
        assertEquals(DivoLogAppender.getCollectionName(), collection.namespace.collectionName)
    }


    // Implement the mocked object of ILoggingEvent
    private fun createMockLoggingEvent(
        level: Level? = Level.INFO,
        formattedMessage: String? = testMessage,
        timeStamp: Long
    ): ILoggingEvent {
        val loggingEvent = Mockito.mock(ILoggingEvent::class.java)
        `when`(loggingEvent.level).thenReturn(level)
        `when`(loggingEvent.formattedMessage).thenReturn(formattedMessage)
        `when`(loggingEvent.timeStamp).thenReturn(timeStamp)
        return loggingEvent
    }
}