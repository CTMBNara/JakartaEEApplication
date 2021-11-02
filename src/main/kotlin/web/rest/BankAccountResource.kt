package web.rest

import core.web.http.annotation.Mapping
import core.web.http.controller.Controller
import core.web.http.view.View
import jakarta.servlet.http.HttpServletRequest
import service.BankAccountService
import web.view.BankAccountView
import web.view.BankAccountsView

class BankAccountResource(private val bankAccountService: BankAccountService) : Controller {
    override fun getRequestMapping(): String = "/bank-account"

    @Mapping("/all")
    suspend fun findAll(request: HttpServletRequest): View =
        BankAccountsView(bankAccountService.findAll().toList())

    @Mapping("/\\w{29}")
    suspend fun findOne(request: HttpServletRequest): View =
        request.requestURI.split("/").last().let {
            BankAccountView(bankAccountService.findById(it))
        }
}
