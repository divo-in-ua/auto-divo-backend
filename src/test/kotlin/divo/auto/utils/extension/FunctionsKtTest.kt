package divo.auto.utils.extension

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.Collections
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

class FunctionsKtTest {
    @Test
    fun `test HttpServletRequest getHttpHeaders`() {
        // Mocking HttpServletRequest
        val request = mock(HttpServletRequest::class.java)

        // Mocking header names and values
        `when`(request.headerNames).thenReturn(Collections.enumeration(listOf("Content-Type", "Accept")))
        `when`(request.getHeaders("Content-Type")).thenReturn(Collections.enumeration(listOf("application/json")))
        `when`(request.getHeaders("Accept")).thenReturn(Collections.enumeration(listOf("text/plain", "application/xml")))

        // Calling the function
        val result = request.getHttpHeaders()

        // Asserting the result
        val expectedHeaders = HttpHeaders()
        expectedHeaders["Content-Type"] = listOf("application/json")
        expectedHeaders["Accept"] = listOf("text/plain", "application/xml")

        assertEquals(expectedHeaders, result)
    }
}