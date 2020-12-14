package ru.degus.cnjoke.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import ru.degus.cnjoke.models.Value
import ru.degus.cnjoke.repository.IJokeRepo
import javax.inject.Inject

class MainViewModel : AbstractViewModel() {

    var requestText = ""  //поле для строки запроса из editText

    @Inject
    lateinit var jokeRepo: IJokeRepo

    val jokesData: MutableLiveData<List<Value>> by lazy { MutableLiveData<List<Value>>() }      //данные списка
    val errorData: MutableLiveData<Throwable> by lazy { MutableLiveData<Throwable>() }          //данные ошибки

    fun downloadJokes() {
            addDisposable(
                    jokeRepo.downloadJokes(requestText.toInt())                         //запрос к сети
                            .subscribe({
                                Log.d("LoadJokes", it.value.size.toString())
                                jokesData.value = it.value                              //установка списка шуток в LIveData
                            }, {
                                errorData.value = it
                                Log.d("LoadJokes", it.toString())                   //установка ошибки в LIveData
                            })
            )
        }
}