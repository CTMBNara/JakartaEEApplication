import core.database.connection.impl.JdbcConnectionPoolImpl
import core.web.http.controller.Controller
import core.web.http.controller.impl.HandlerMappingImpl
import core.web.http.servlet.DispatcherServlet
import org.apache.catalina.Context
import org.apache.catalina.startup.Tomcat
import repository.impl.BankAccountRepositoryImpl
import service.impl.BankAccountServiceImpl
import web.rest.BankAccountResource
import java.io.File
import java.util.*

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

private fun controllers(): Collection<Controller> {
    val bundle = ResourceBundle.getBundle("database")
    val connectionPool = JdbcConnectionPoolImpl(
        dataSourceUrl = bundle.getString("url"),
        user = bundle.getString("user"),
        password = bundle.getString("password")
    )

    val bankAccountRepository = BankAccountRepositoryImpl { connectionPool }

    val bankAccountService = BankAccountServiceImpl(bankAccountRepository)

    val bankAccountResource = BankAccountResource(bankAccountService)

    return listOf(bankAccountResource)
}

