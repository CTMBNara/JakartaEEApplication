package repository

import database.repository.CrudRepository
import domain.BankAccount

interface BankAccountRepository : CrudRepository<BankAccount, String>
