package domain

data class Publisher(
    val id: Long,
    val name: String,
    val email: String,
    val bankAccount: BankAccount,
    val info: String
)
