import database.connection.impl.JdbcConnectionPoolImpl
import domain.BankAccount
import kotlinx.coroutines.*
import repository.impl.BankAccountRepositoryImpl
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main(args: Array<String>): Unit = runBlocking {
    val bundle = ResourceBundle.getBundle("database")
    val connectionPool = JdbcConnectionPoolImpl(
        dataSourceUrl = bundle.getString("url"),
        user = bundle.getString("user"),
        password = bundle.getString("password")
    )
    val bankAccountRepository = BankAccountRepositoryImpl { connectionPool }

    val jobs = mutableListOf<Job>()
    val a = AtomicInteger(0)
    val time = measureTimeMillis {
        repeat(100000) {
            launch(Dispatchers.IO) {
                val iban = a.getAndIncrement().toString().padStart(29, '0')
                bankAccountRepository.save(BankAccount(iban))
            }.also { jobs.add(it) }
        }
    }

    jobs.joinAll()
    println("Save time: $time")

    println(bankAccountRepository.count())

    bankAccountRepository.deleteAll()

    println(bankAccountRepository.findAll())
}
