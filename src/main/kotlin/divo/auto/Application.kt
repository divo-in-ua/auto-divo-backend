package divo.auto

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/** SpringBootApplication annotation is a convenience annotation in a Spring Boot application. It enables Spring Boot's
 * auto-configuration, component scan, and be able to define an extra configuration on their "application class" */
@SpringBootApplication
class Application {
    // nothing for now
}

/** The main() function is the entry point to the application. It invokes the Spring's runApplication(&args) function to
 * start the application with the Spring Framework. */
fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
