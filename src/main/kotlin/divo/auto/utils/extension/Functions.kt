package divo.auto.utils.extension

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors


/** Extract HTTP headers from an HttpServletRequest and represent them as a HttpHeaders object. */
fun HttpServletRequest.getHttpHeaders(): HttpHeaders {
    return Collections
        .list(this.headerNames) // Get a list of header names
        .stream() // Convert the list to a stream for processing
        .collect( // Collect the stream elements into a result
            Collectors.toMap(
                Function.identity(), // Key mapper: identity function (returns the element itself)
                { header -> // Value mapper: for each header name, get a list of header values
                    Collections.list(
                        this.getHeaders(header)
                    )
                },
                { _, newValue -> newValue }, // Merge function: if there are duplicate keys, choose the new value
                { HttpHeaders() } // Supplier: create a new HttpHeaders instance
            )
        )
}