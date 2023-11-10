package divo.auto.configurations

import divo.auto.utils.logs.DivoRequestLoggingInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/** Configure interceptors to be used in the Spring application */
@Configuration
class DivoWebMvcConfigurations: WebMvcConfigurer {
    @Autowired
    private lateinit var divoRequestLoggingInterceptor: DivoRequestLoggingInterceptor
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(divoRequestLoggingInterceptor)
    }
}