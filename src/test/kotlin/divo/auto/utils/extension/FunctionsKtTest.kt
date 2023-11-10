package divo.auto.utils.extension

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.Collections
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
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

    @Test
    fun `test HttpServletResponse getHttpHeaders`() {
        // Mocking HttpServletResponse
        val response = mock(HttpServletResponse::class.java)

        // Mocking header names and values
        `when`(response.headerNames).thenReturn(setOf("Content-Type", "Cache-Control"))
        `when`(response.getHeader("Content-Type")).thenReturn("application/json")
        `when`(response.getHeader("Cache-Control")).thenReturn("no-store, max-age=3600")

        // Calling the function
        val result = response.getHttpHeaders()

        // Asserting the result
        val expectedHeaders = HttpHeaders()
        expectedHeaders["Content-Type"] = listOf("application/json")
        expectedHeaders["Cache-Control"] = listOf("no-store, max-age=3600")

        assertEquals(expectedHeaders, result)
    }
}