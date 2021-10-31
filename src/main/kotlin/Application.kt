import org.apache.catalina.startup.Tomcat
import servlet.DispatcherServlet
import java.io.File

fun main() {
    val docBase = "src/main/webapp/"
    val port = 8080
    val contextPath = "/api"
    val pattern = "/*"

    val servlet = DispatcherServlet()

    Tomcat().apply {
        setPort(port)
        connector

        val context = addWebapp(contextPath, File(docBase).absolutePath)
        Tomcat.addServlet(context, servlet::javaClass.name, servlet)
        context.addServletMappingDecoded(pattern, servlet::javaClass.name)
    }.also {
        it.start()
        it.server.await()
    }
}
