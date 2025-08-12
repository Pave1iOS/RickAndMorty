package com.example.rickandmorty.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.App

@Composable
inline fun <reified VM : ViewModel> daggerViewModel(): VM {
    val factory: ViewModelProvider.Factory =
        App.instance.appComponent.viewModelFactory()
    return viewModel(factory = factory)
}
