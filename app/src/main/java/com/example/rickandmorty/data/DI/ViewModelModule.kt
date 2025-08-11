package com.example.rickandmorty.data.DI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.presentation.screens.mainScreen.MainScreenViewModel
import com.example.rickandmorty.domain.ViewModelFactory
import com.example.rickandmorty.presentation.screens.detailScreen.CharacterDetailsViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainScreenViewModel::class)
    abstract fun bindFirstActivityViewModel(viewModel: MainScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    abstract fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)