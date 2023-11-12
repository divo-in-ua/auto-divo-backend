package divo.auto.utils.logs

import divo.auto.utils.extension.getBody
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class DivoRequestLoggingInterceptor: HandlerInterceptor {

    val logger: Logger = LoggerFactory.getLogger(this@DivoRequestLoggingInterceptor.javaClass)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val requestLog = "${request.method} ${request.requestURI} body: ${request.getBody()}"
        logger.info("REQUEST ---->: $requestLog")
        return true
    }
}