package ru.degus.cnjoke.di.modules

import dagger.Module
import dagger.Provides
import ru.degus.cnjoke.api.ApiFactory
import ru.degus.cnjoke.repository.IJokeRepo
import ru.degus.cnjoke.repository.JokeRepo
import javax.inject.Singleton

@Module(includes = [ApiFactoryModule::class])
class JokeRepoModule {

    @Singleton //метод предоставляющий JokeRepo
    @Provides
    fun getJokeRepo(apiFactory: ApiFactory): IJokeRepo {
        return JokeRepo(apiFactory)
    }

}