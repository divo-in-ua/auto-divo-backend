package divo.auto.utils.logs

import divo.auto.utils.extension.getHttpHeaders
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import java.util.stream.Collectors

@Component
class DivoRequestLoggingInterceptor: HandlerInterceptor {

    val logger: Logger = LoggerFactory.getLogger(this@DivoRequestLoggingInterceptor.javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val body = request.reader.lines().collect(Collectors.joining(System.lineSeparator()))
        val headers = request.getHttpHeaders().toString()
        val requestLog = "${request.method} ${request.requestURI} headers: $headers. body: $body"
        logger.info("REQUEST ---->: $requestLog")
        return true
    }
}