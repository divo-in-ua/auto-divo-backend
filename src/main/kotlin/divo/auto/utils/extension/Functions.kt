package divo.auto.utils.extension

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.util.CollectionUtils
import org.springframework.util.MultiValueMap
import org.springframework.web.util.ContentCachingRequestWrapper
import java.util.*
import java.util.function.Function
import java.util.stream.Collectors
import kotlin.collections.HashMap


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
                { _, newValue -> newValue }, // Merge function: if there are duplicate values, choose the new value
                { HttpHeaders() } // Supplier: create a new HttpHeaders instance
            )
        )
}

/** Extract HTTP headers from an HttpServletRequest and represent them as a HttpHeaders object. */
fun HttpServletResponse.getHttpHeaders(): HttpHeaders {
    val result: MultiValueMap<String, String> = this.headerNames
        .stream()
        .collect(
            { CollectionUtils.toMultiValueMap(HashMap()) }, // Supplier
            { map, header -> map[header] = listOf(getHeader(header)) }, // Accumulator
            { map1, map2 -> map1.putAll(map2) } // Combiner
        )
    return HttpHeaders(result)
}

/** Wrap request if not wrapped yet and get a body from an input stream */
fun HttpServletRequest.getBody(): String {
    val contentCachingRequestWrapper = if (this is ContentCachingRequestWrapper) {
        this
    } else {
        ContentCachingRequestWrapper(this)
    }
    return contentCachingRequestWrapper.inputStream.bufferedReader().use { it.readText() }
}