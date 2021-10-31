package service

import domain.BankAccount

interface BankAccountService {
    suspend fun save(entity: BankAccount)
    suspend fun saveAll(entities: Iterable<BankAccount>)

    suspend fun findAll(): Collection<BankAccount>
    suspend fun findAllById(ids: Iterable<String>): Collection<BankAccount>
    suspend fun findById(id: String): BankAccount?

    suspend fun delete(entity: BankAccount)
    suspend fun deleteAll()
    suspend fun deleteAll(entities: Iterable<BankAccount>)
    suspend fun deleteAllById(ids: Iterable<String>)
    suspend fun deleteById(id: String)

    suspend fun count(): Long
    suspend fun existsById(id: String): Boolean
}
