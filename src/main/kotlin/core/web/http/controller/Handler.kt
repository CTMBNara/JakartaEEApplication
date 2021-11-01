package core.web.http.controller

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse

fun interface Handler {
    fun handle(request: HttpServletRequest, response: HttpServletResponse): String
}
