package core.database.utils

fun <T> Iterable<T>.toValues(transform: (T) -> String): String =
    joinToString(separator = ",") { "(" + transform(it) + ")" }

fun <T> Iterable<T>.toIn(transform: (T) -> String): String =
    joinToString(separator = ",", prefix = "(", postfix = ")", transform = transform)
