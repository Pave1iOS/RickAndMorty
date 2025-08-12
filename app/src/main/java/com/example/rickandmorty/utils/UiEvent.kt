package com.example.rickandmorty.utils

import androidx.annotation.StringRes

sealed class UiEvent {
    data class ShowMessage(@StringRes val messageRes: Int) : UiEvent()
}