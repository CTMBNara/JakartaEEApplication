package web.view

import core.web.http.exception.NotFoundException
import core.web.http.view.View
import domain.BankAccount
import jakarta.servlet.http.HttpServletRequest

class BankAccountView(private val bankAccount: BankAccount?) : View {
    override fun includeIn(request: HttpServletRequest): View =
        also {
            if (bankAccount == null) throw NotFoundException()
            else request.setAttribute("bank_account", bankAccount)
        }

    override fun getRequestDispatcherSource(): String = "/bankAccount/single.jsp"
}

class BankAccountsView(private val bankAccounts: List<BankAccount>) : View {
    override fun includeIn(request: HttpServletRequest): View =
        also { request.setAttribute("bank_accounts", bankAccounts) }

    override fun getRequestDispatcherSource(): String = "/bankAccount/all.jsp"
}
