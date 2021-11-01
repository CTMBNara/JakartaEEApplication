package core.web.http.controller.impl

import core.web.http.annotation.Mapping
import core.web.http.controller.Controller
import core.web.http.controller.Handler
import core.web.http.controller.HandlerMapping
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

class HandlerMappingImpl(controllers: Collection<Controller>) : HandlerMapping {
    private val controllersMap: Map<String, Controller>

    init {
        controllersMap = controllers.associateBy(Controller::requestMapping)
        require(controllersMap.size == controllers.size) {
            "Several controllers have the same mapping."
        }
    }

    override fun getHandler(requestUri: String): Handler? {
        val controllerEntry = controllersMap
            .asIterable()
            .first { requestUri.startsWith(it.key) }

        val uri = requestUri.drop(controllerEntry.key.length)

        val method = controllerEntry.value::class.java.declaredMethods.firstOrNull { method ->
            val annotation = method.getAnnotation(Mapping::class.java)
            annotation != null && uri.matches(Regex(annotation.mapping))
        } ?: return null

        require(method.returnType == String::class.java)
        require(method.parameterCount == 2)
        require(method.parameterTypes[0] == HttpServletRequest::class.java)
        require(method.parameterTypes[1] == HttpServletResponse::class.java)

        return Handler { handledRequest, handledResponse ->
            method.invoke(controllerEntry.value, handledRequest, handledResponse) as String
        }
    }
}
