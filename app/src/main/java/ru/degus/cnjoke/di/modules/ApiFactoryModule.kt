package ru.degus.cnjoke.di.modules

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import ru.degus.cnjoke.api.ApiFactory
import ru.degus.cnjoke.components.CHUCK_URL
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApiFactoryModule {

    @Singleton              //метод предоставляющий Api с базовым url и логгером
    @Provides
    fun apiFactory(@Named("iChuckUrl") iTunsUrl: String, @Named("okhhtp_logging")client: OkHttpClient): ApiFactory {
        return ApiFactory(iTunsUrl, client)
    }

    @Provides
    @Named("iChuckUrl")      //метод предоставляющий базовый url
    fun iTunsUrl() = CHUCK_URL

    @Provides
    @Named("okhhtp_logging")        //метод предоставляющий логгер для отслеживания запросов в сеть
    fun getOkHttpClient(): OkHttpClient {
        val c = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("OkHttpLogger", it)
        })

        logging.level = HttpLoggingInterceptor.Level.BASIC
        c.addInterceptor(logging)
        return c.build()
    }
}