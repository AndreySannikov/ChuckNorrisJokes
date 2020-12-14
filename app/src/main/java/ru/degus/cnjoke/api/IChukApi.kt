package ru.degus.cnjoke.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import ru.degus.cnjoke.models.Response

interface IChuckApi {
    @GET("jokes/random/{number}")
    fun getJokes(@Path("number")number: Int): Observable<Response> // get запрос на сервер , возвращающий указанное колличество шуток
}
