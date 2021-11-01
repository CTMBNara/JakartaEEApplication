package core.web.http.controller

interface HandlerMapping {
    fun getHandler(requestUri: String): Handler?
}
