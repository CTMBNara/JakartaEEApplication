package core.database.utils

import core.database.connection.JdbcConnectionPool
import java.sql.Connection
import java.sql.ResultSet

fun interface QueryExecutor {
    fun getJdbcConnectionPool(): JdbcConnectionPool

    suspend fun <T> runWithConnection(runnable: (Connection) -> T): T {
        val connectionPool = getJdbcConnectionPool()
        val connection = connectionPool.getConnection()
        try {
            return runnable(connection)
        } finally {
            connectionPool.releaseConnection(connection)
        }
    }

    suspend fun executeQuery(sql: String): Unit =
        runWithConnection { it.createStatement().execute(sql) }

    suspend fun <T> executeQuery(sql: String, transform: (ResultSet) -> T): T =
        runWithConnection { transform(it.createStatement().executeQuery(sql)) }
}
