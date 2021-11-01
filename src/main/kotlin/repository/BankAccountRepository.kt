package repository

import core.database.repository.CrudRepository
import domain.BankAccount

interface BankAccountRepository : CrudRepository<BankAccount, String>
