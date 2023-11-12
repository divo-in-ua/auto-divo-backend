package divo.auto.configurations

import divo.auto.utils.extension.getBody
import divo.auto.utils.extension.getHttpHeaders
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.Customizer
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Configuration
@EnableWebSecurity
class DivoApiSecurityConfig {

    val logger: Logger = LoggerFactory.getLogger(DivoApiSecurityConfig::class.java.name)

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        httpSecurity
            .csrf { csrf ->
                csrf.ignoringRequestMatchers("/api/**", "/api**")
            }
            .formLogin { form ->
                form.disable()
            }
            .authorizeHttpRequests { request ->
                request
                    .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                    .requestMatchers("/**").authenticated()
            }
            .exceptionHandling(getExceptionHandler())
            .httpBasic(withDefaults())
        return httpSecurity.build()
    }

    private fun getExceptionHandler(): Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
        return Customizer<ExceptionHandlingConfigurer<HttpSecurity>> { configuration ->
            configuration.authenticationEntryPoint(getAuthenticationEntryPoint())
        }
    }

    private fun getAuthenticationEntryPoint(): AuthenticationEntryPoint {
        return object : AuthenticationEntryPoint {
            override fun commence(
                httpServletRequest: HttpServletRequest?,
                httpServletResponse: HttpServletResponse?,
                authException: AuthenticationException?
            ) {
                if (httpServletRequest != null) {
                    val request = ContentCachingRequestWrapper(httpServletRequest)
                    val url = "${request.method} ${request.requestURI}"
                    val headers = "${request.getHttpHeaders()}"
                    val body = request.getBody()
                    logger.error("AuthError $url, headers: $headers, body: $body")
                }
                if (httpServletResponse == null) return
                val response = ContentCachingResponseWrapper(httpServletResponse)
                val errorMessage = "${authException?.message ?: ""} ... Do you have a token?"
                val errorCode = HttpStatus.UNAUTHORIZED.value()
                response.writer.write(errorMessage)
                response.status = errorCode
                response.copyBodyToResponse()
                logger.error("Return $errorCode $errorMessage")
            }
        }
    }
}