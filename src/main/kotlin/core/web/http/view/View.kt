package core.web.http.view

import jakarta.servlet.http.HttpServletRequest

interface View {
    fun includeIn(request: HttpServletRequest): View?
    fun getRequestDispatcherSource(): String
}
