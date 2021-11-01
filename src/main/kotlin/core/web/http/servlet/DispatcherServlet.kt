package core.web.http.servlet

import core.web.http.controller.HandlerMapping
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class DispatcherServlet(
    val pattern: String,
    private val handlerMapping: HandlerMapping
) : HttpServlet() {
    private val patternLength = pattern.removeSuffix("/*").length

    override fun service(req: HttpServletRequest, resp: HttpServletResponse) {
        val handlerUri = req.requestURI.drop(patternLength)

        handlerMapping.getHandler(handlerUri)?.also {
            servletContext
                .getRequestDispatcher(it.handle(req, resp))
                .forward(req, resp)
        } ?: super.service(req, resp)
    }
}
