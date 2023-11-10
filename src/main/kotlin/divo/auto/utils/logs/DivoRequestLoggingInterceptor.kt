package divo.auto.utils.logs

import divo.auto.entities.DivoLogEntityService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView

@Component
class DivoRequestLoggingInterceptor: HandlerInterceptor {

    val logger: Logger = LoggerFactory.getLogger(this@DivoRequestLoggingInterceptor.javaClass)

    @Autowired
    private lateinit var logService: DivoLogEntityService

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("DivoRequestLoggingInterceptor preHandle!")
        logger.info("DivoRequestLoggingInterceptor preHandle!")
        return true
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) {
//        println("Sent response: ${response.status} ${ response }")
        println("DivoRequestLoggingInterceptor postHandle!")
        logger.info("DivoRequestLoggingInterceptor postHandle!")
        // This method is called after the request has been handled by the controller
    }
}