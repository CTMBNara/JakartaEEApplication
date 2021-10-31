package domain

import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime

data class LibraryRecord(
    val id: Long,
    val user: User,
    val program: Program,
    val purchaseDate: LocalDateTime,
    val usageTime: Duration,
    val purchasePrice: BigDecimal
)
