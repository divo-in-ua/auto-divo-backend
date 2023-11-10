package divo.auto.utils.logs

import divo.auto.entities.DivoLogEntityService
import divo.auto.utils.extension.getHttpHeaders
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import java.util.stream.Collectors

@Component
class DivoRequestLoggingInterceptor: HandlerInterceptor {

    val logger: Logger = LoggerFactory.getLogger(this@DivoRequestLoggingInterceptor.javaClass)

    @Autowired
    private lateinit var logService: DivoLogEntityService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val body = request.reader.lines().collect(Collectors.joining(System.lineSeparator()))
        val headers = request.getHttpHeaders().toString()
        val requestLog = "${request.method} ${request.requestURI} headers: $headers. body: $body"
        logger.info("REQUEST ---->: $requestLog")
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
//        println("Sent response: ${response.status} ${ response }")
//        val contentAsByteArray = (response as ContentCachingRequestWrapper).contentAsByteArray
//        val body = String(contentAsByteArray)
        response.headerNames
        val responseLog = "RESPONSE <----: ${response.status}"
        logger.info(responseLog)
        // This method is called after the request has been handled by the controller
    }
}