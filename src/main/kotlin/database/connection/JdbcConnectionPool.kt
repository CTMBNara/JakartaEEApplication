package database.connection

import java.sql.Connection

interface JdbcConnectionPool {
    suspend fun getConnection(): Connection
    suspend fun releaseConnection(connection: Connection)
}
