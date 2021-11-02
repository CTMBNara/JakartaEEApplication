package core.database.utils

import java.sql.ResultSet
import java.util.stream.Collector

fun <T> Iterable<T>.toValues(transform: (T) -> String): String =
    joinToString(separator = ",") { "(" + transform(it) + ")" }

fun <T> Iterable<T>.toIn(transform: (T) -> String): String =
    joinToString(separator = ",", prefix = "(", postfix = ")", transform = transform)

fun <T, MC, C> ResultSet.collect(collector: Collector<in T, MC, C>, transform: (ResultSet) -> T): C {
    val resultCollection = collector.supplier().get()
    while (next()) collector.accumulator().accept(resultCollection, transform(this))
    return collector.finisher().apply(resultCollection)
}
