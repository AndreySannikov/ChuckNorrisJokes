package ru.degus.cnjoke.repository

import io.reactivex.Observable
import ru.degus.cnjoke.models.Response

interface IJokeRepo {
    fun downloadJokes(number: Int): Observable<Response>  //загрузка списка шуток с указанной длинной
}