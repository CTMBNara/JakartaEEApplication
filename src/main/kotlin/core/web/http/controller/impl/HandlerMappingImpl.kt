package core.web.http.controller.impl

import core.web.http.annotation.Mapping
import core.web.http.controller.Controller
import core.web.http.controller.Handler
import core.web.http.controller.HandlerMapping
import java.lang.reflect.Proxy

class HandlerMappingImpl(controllers: Collection<Controller>) : HandlerMapping {
    private val controllersMap: Map<String, Controller>

    init {
        controllersMap = controllers.associateBy(Controller::getRequestMapping)
        require(controllersMap.size == controllers.size) {
            "Several controllers have the same mapping."
        }
    }

    override fun getHandler(requestUri: String): Handler? {
        val controllerEntry = controllersMap
            .asIterable()
            .first { requestUri.startsWith(it.key) }

        val uri = requestUri.drop(controllerEntry.key.length)

        val handler = controllerEntry.value::class.java.declaredMethods.firstOrNull { method ->
            val annotation = method.getAnnotation(Mapping::class.java)
            annotation != null && uri.matches(Regex(annotation.mapping))
        } ?: return null

        return Proxy.newProxyInstance(
            Handler::class.java.classLoader,
            arrayOf(Handler::class.java)
        ) { _, _, args ->
            val nonNullArgs = args ?: arrayOf()
            handler.invoke(controllerEntry.value, *nonNullArgs)
        } as Handler
    }
}
