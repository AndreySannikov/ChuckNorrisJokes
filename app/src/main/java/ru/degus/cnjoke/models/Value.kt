package ru.degus.cnjoke.models

//класс описывающий шутки, приходящие с сервера
data class Value(
    val categories: List<Any>,
    val id: Int,
    val joke: String,
    var expanded: Boolean = false
)