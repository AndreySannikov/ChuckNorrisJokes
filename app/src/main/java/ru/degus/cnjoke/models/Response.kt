package ru.degus.cnjoke.models

//класс ответа с сервера
data class Response(
    val type: String,
    val value: List<Value>
)