package core.web.http.controller

import core.web.http.view.View
import jakarta.servlet.http.HttpServletRequest

fun interface Handler {
    suspend fun handle(request: HttpServletRequest): View
}
