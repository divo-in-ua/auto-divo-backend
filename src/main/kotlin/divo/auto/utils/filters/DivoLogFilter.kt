package divo.auto.utils.filters

import divo.auto.utils.extension.getHttpHeaders
import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

/** The purpose of this filter is to capture and cache the request and response content,
 * providing the ability to inspect or modify it later in the filter chain or in subsequent processing stages.
 * This can be useful for scenarios such as logging, auditing, or custom processing of request and response content.
 */
@Component
class DivoLogFilter: Filter {

    val logger: Logger = LoggerFactory.getLogger(this@DivoLogFilter.javaClass)

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val wrappedRequest = ContentCachingRequestWrapper(request as HttpServletRequest)
        val wrappedResponse = ContentCachingResponseWrapper(response as HttpServletResponse)
        chain?.doFilter(wrappedRequest, wrappedResponse)
        wrappedResponse.copyBodyToResponse()
        wrappedResponse.copyBodyToResponse()
        logResponse(wrappedResponse)
    }

    private fun logResponse(response: ContentCachingResponseWrapper) {
        val headers = response.getHttpHeaders()
        val responseLog = "RESPONSE headers: $headers"
        logger.info(responseLog)
    }
}