import core.web.http.annotation.Mapping
import core.web.http.controller.Controller
import core.web.http.controller.impl.HandlerMappingImpl
import core.web.http.servlet.DispatcherServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import java.io.File

fun main() {
    val docBase = "src/main/webapp/"
    val port = 8080
    val contextPath = ""
    val pattern = "/api/*"

    val tomcat = Tomcat()
    tomcat.setPort(port)
    tomcat.connector

    val context = tomcat.addWebapp(contextPath, File(docBase).absolutePath)
    registerDispatcherServlet(context, pattern)

    tomcat.start()
    tomcat.server.await()
}

private fun registerDispatcherServlet(context: Context, pattern: String) {
    val controllers = controllers()
    val handlerMapping = HandlerMappingImpl(controllers)
    val servlet = DispatcherServlet(pattern, handlerMapping)

    Tomcat.addServlet(context, servlet::class.java.name, servlet)
    context.addServletMappingDecoded(servlet.pattern, servlet::class.java.name)
}

private fun controllers(): Collection<Controller> = listOf(
    object : Controller {
        override val requestMapping: String
            get() = "/main"

        @Mapping("/test")
        fun test(request: HttpServletRequest, response: HttpServletResponse): String {
            println("yes")
            return "/main.jsp"
        }
    }
)

