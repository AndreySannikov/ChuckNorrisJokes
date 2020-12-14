package ru.degus.cnjoke.di

import androidx.appcompat.app.AppCompatActivity
import dagger.BindsInstance
import dagger.Component
import dagger.Subcomponent
import ru.degus.cnjoke.App
import ru.degus.cnjoke.di.modules.ActivityModule
import ru.degus.cnjoke.di.modules.ApiFactoryModule
import ru.degus.cnjoke.di.modules.JokeRepoModule
import ru.degus.cnjoke.fragments.MainFragment
import ru.degus.cnjoke.fragments.WebFragment
import ru.degus.cnjoke.viewmodels.MainViewModel
import javax.inject.Singleton

@Component(
    modules = [
        ApiFactoryModule::class,
        JokeRepoModule::class
    ]
)
@Singleton
interface AppComponent {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        @BindsInstance
        fun application(appModule: App): Builder
    }

    fun viewModelSubComponentBuilder(): ViewModelSubComponent.Builder                   //методы создания sub компонентов
    fun activitySubComponentBuilder(): ActivitySubComponent.Builder
}

@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun inject(vModel: MainViewModel)                                                   //методы инъекции зависимостей во viewModel

}

@Subcomponent(modules = [ActivityModule::class])
interface ActivitySubComponent {
    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun with(activity: AppCompatActivity): Builder

        fun build(): ActivitySubComponent
    }

    fun inject(mainFragment: MainFragment)                                              //методы инъекции зависимостей во фрагменты
    fun inject(webFragment: WebFragment)
}