package ru.degus.cnjoke.repository

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.degus.cnjoke.api.ApiFactory
import ru.degus.cnjoke.models.Response

class JokeRepo(val apiFactory: ApiFactory) : IJokeRepo {

    override fun downloadJokes(number: Int): Observable<Response> {  //получение списка шуток в фоновом потоке
        return apiFactory.getIChuckApi().getJokes(number)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}