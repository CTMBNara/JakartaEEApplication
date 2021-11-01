package core.database.connection.impl

import core.database.connection.JdbcConnectionPool
import kotlinx.coroutines.channels.Channel
import java.sql.Connection
import java.sql.DriverManager
import java.util.concurrent.ConcurrentSkipListSet

class JdbcConnectionPoolImpl(
    private val dataSourceUrl: String,
    private val user: String,
    private val password: String
) : JdbcConnectionPool {

    private val threadLocalConnection = ThreadLocal.withInitial {
        DriverManager.getConnection(
            dataSourceUrl,
            user,
            password
        )
    }

    private val threadLocalChannel = ThreadLocal.withInitial {
        Channel<Connection>(Channel.BUFFERED)
    }

    private val connection: Connection
        inline get() = threadLocalConnection.get()

    private val channel: Channel<Connection>
        inline get() = threadLocalChannel.get()

    private val usedThreads = ConcurrentSkipListSet<String>()

    override suspend fun getConnection(): Connection {
        val threadName = Thread.currentThread().name
        if (!usedThreads.contains(threadName)) {
            usedThreads.add(threadName)
            channel.send(connection)
        }

        return channel.receive()
    }

    override suspend fun releaseConnection(connection: Connection) {
        require(this.connection == connection) {
            "Unable to release a connection that does not belong to this thread."
        }
        channel.send(this.connection)
    }
}
