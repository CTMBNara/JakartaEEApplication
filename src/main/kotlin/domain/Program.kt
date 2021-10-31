package domain

import java.math.BigDecimal

data class Program(
    val id: Long,
    val name: String,
    val genres: Set<Genre>,
    val publisher: Publisher,
    val price: BigDecimal
)
