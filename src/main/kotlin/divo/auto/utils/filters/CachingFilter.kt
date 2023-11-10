package divo.auto.utils.filters

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

/** The purpose of this filter is to capture and cache the request and response content,
 * providing the ability to inspect or modify it later in the filter chain or in subsequent processing stages.
 * This can be useful for scenarios such as logging, auditing, or custom processing of request and response content.
 */
@Component
class CachingFilter: OncePerRequestFilter() {

    // It contains the logic for the filter to be applied to incoming requests.
    override fun doFilterInternal(
        request: HttpServletRequest, // Represents the client's request to the server.
        response: HttpServletResponse, // Represents the response from the server to the client.
        filterChain: FilterChain //  Allows the request to proceed to the next filter in the chain.
    ) {
        // This line invokes the next entity in the filter chain. However, there's a modification to the typical
        // doFilter method. Instead of passing the original request and response objects, it wraps them with
        // ContentCachingRequestWrapper and ContentCachingResponseWrapper respectively. This is allowing it to be
        // captured and inspected after the response is committed.
        val wrappedRequest = ContentCachingRequestWrapper(request)
        val wrappedResponse = ContentCachingResponseWrapper(response)
        filterChain.doFilter(wrappedRequest, wrappedResponse)
        wrappedResponse.copyBodyToResponse()
    }
}