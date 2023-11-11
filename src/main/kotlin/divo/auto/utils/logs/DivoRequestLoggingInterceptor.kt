package divo.auto.utils.logs

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
        // if needed you can get also it:
        // val headers = request.getHttpHeaders().toString()

        val body = request.inputStream.bufferedReader().use { it.readText() }
        val requestLog = "${request.method} ${request.requestURI} body: $body"
        logger.info("REQUEST ---->: $requestLog")

        return true
    }
}