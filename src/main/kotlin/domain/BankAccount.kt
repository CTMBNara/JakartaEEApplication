package domain

data class BankAccount(val iban: String) {
    init {
        require(iban.length <= 29) { "Invalid iban." }
    }
}
