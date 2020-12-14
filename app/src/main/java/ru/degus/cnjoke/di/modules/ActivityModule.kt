package ru.degus.cnjoke.di.modules

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import ru.degus.cnjoke.viewmodels.MainViewModel


@Module
class ActivityModule {
    @Provides       //Метод предоставляющий MainViewModel
    fun getMainViewModel(activity: AppCompatActivity): MainViewModel {
        return ViewModelProvider(activity).get(MainViewModel::class.java)
    }
}