package core.web.http.servlet

import core.web.http.controller.HandlerMapping
import core.web.http.exception.NotFoundException
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import kotlinx.coroutines.runBlocking

class DispatcherServlet(
    val pattern: String,
    private val handlerMapping: HandlerMapping
) : HttpServlet() {
    private val patternLength = pattern.removeSuffix("/*").length

    override fun service(req: HttpServletRequest, resp: HttpServletResponse): Unit = runBlocking {
        val handlerUri = req.requestURI.drop(patternLength)

        handleException(resp) {
            handlerMapping.getHandler(handlerUri)
                ?.handle(req)
                ?.includeIn(req)
                ?.getRequestDispatcherSource()
                ?.let(req::getRequestDispatcher)
                ?.forward(req, resp)
                ?: super.service(req, resp)
        }
    }

    private inline fun handleException(resp: HttpServletResponse, block: () -> Unit) {
        runCatching { block() }.onFailure {
            when (it) {
                is NotFoundException -> resp.sendError(HttpServletResponse.SC_NOT_FOUND)
                else -> throw it
            }
        }
    }
}
