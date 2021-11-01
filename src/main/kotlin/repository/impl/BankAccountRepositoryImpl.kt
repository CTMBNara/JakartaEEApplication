package repository.impl

import core.database.utils.QueryExecutor
import core.database.utils.toIn
import core.database.utils.toValues
import domain.BankAccount
import repository.BankAccountRepository
import java.util.*

class BankAccountRepositoryImpl(
    private val queryExecutor: QueryExecutor
) : BankAccountRepository {
    override suspend fun save(entity: BankAccount): Unit = queryExecutor
        .executeQuery("INSERT INTO bank_account(iban) VALUES ('${entity.iban}')")

    override suspend fun saveAll(entities: Iterable<BankAccount>): Unit = queryExecutor
        .executeQuery("INSERT INTO bank_account(iban) VALUES ${entities.toValues { "'${it.iban}'" }}")

    override suspend fun findAll(): Collection<BankAccount> = queryExecutor
        .executeQuery("SELECT * FROM bank_account") { result ->
            val resultSet = mutableListOf<BankAccount>()
            while (result.next()) resultSet.add(
                BankAccount(result.getString(1))
            )
            Collections.unmodifiableCollection(resultSet)
        }

    override suspend fun findAllById(ids: Iterable<String>): Collection<BankAccount> = queryExecutor
        .executeQuery("SELECT * FROM bank_account WHERE iban in ${ids.toIn { "'$it'" }}") { result ->
            val resultSet = mutableListOf<BankAccount>()
            while (result.next()) resultSet.add(
                BankAccount(result.getString(1))
            )
            Collections.unmodifiableCollection(resultSet)
        }

    override suspend fun findById(id: String): BankAccount? = queryExecutor
        .executeQuery("SELECT * FROM bank_account WHERE iban = '$id'") {
            if (!it.next()) null
            else BankAccount(it.getString(1))
        }

    override suspend fun delete(entity: BankAccount): Unit = queryExecutor
        .executeQuery("DELETE FROM bank_account WHERE iban = '${entity.iban}'")

    override suspend fun deleteAll(): Unit = queryExecutor
        .executeQuery("DELETE FROM bank_account")

    override suspend fun deleteAll(entities: Iterable<BankAccount>): Unit = queryExecutor
        .executeQuery("DELETE FROM bank_account WHERE iban in ${entities.toIn { "'${it.iban}'" }}")

    override suspend fun deleteAllById(ids: Iterable<String>): Unit = queryExecutor
        .executeQuery("DELETE FROM bank_account WHERE iban in ${ids.toIn { "'$it'" }}")

    override suspend fun deleteById(id: String): Unit = queryExecutor
        .executeQuery("DELETE FROM bank_account WHERE iban = '$id'")

    override suspend fun count(): Long = queryExecutor
        .executeQuery("SELECT count(*) FROM bank_account") {
            it.next()
            it.getLong(1)
        }

    override suspend fun existsById(id: String): Boolean = queryExecutor
        .executeQuery("SELECT count(*) FROM bank_account WHERE iban = '$id'") {
            it.next()
            it.getLong(1) > 0
        }
}
