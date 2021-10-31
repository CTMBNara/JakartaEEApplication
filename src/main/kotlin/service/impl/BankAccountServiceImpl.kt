package service.impl

import domain.BankAccount
import repository.BankAccountRepository
import service.BankAccountService

class BankAccountServiceImpl(
    private val bankAccountRepository: BankAccountRepository
) : BankAccountService {
    override suspend fun save(entity: BankAccount): Unit =
        bankAccountRepository.save(entity)

    override suspend fun saveAll(entities: Iterable<BankAccount>): Unit =
        bankAccountRepository.saveAll(entities)

    override suspend fun findAll(): Collection<BankAccount> =
        bankAccountRepository.findAll()

    override suspend fun findAllById(ids: Iterable<String>): Collection<BankAccount> =
        bankAccountRepository.findAllById(ids)

    override suspend fun findById(id: String): BankAccount? =
        bankAccountRepository.findById(id)

    override suspend fun delete(entity: BankAccount): Unit =
        bankAccountRepository.delete(entity)

    override suspend fun deleteAll(): Unit =
        bankAccountRepository.deleteAll()

    override suspend fun deleteAll(entities: Iterable<BankAccount>): Unit =
        bankAccountRepository.deleteAll(entities)

    override suspend fun deleteAllById(ids: Iterable<String>): Unit =
        bankAccountRepository.deleteAllById(ids)

    override suspend fun deleteById(id: String): Unit =
        bankAccountRepository.deleteById(id)

    override suspend fun count(): Long =
        bankAccountRepository.count()

    override suspend fun existsById(id: String): Boolean =
        bankAccountRepository.existsById(id)
}
