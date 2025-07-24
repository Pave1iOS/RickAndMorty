package com.example.rickandmorty.presentation.screens.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.api.Character
import com.example.rickandmorty.domain.NetworkRepository
import com.example.rickandmorty.utils.LogSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val repository: NetworkRepository
): ViewModel() {

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun getAllCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            Log.i(TAG, "${LogSource.VIEWMODEL} start load \n" +
                    "_isLoading = ${_isLoading.value}")

            try {
                val result = repository.getListCharacters().getOrThrow()
                _characters.value = result
                Log.i(TAG, "${LogSource.VIEWMODEL} getAllCharacters = ${_characters.value}")
            } catch (e: Throwable) {
                _errorMessage.value = "Ошибка загрузки данных: ${e.localizedMessage}"
                Log.e(TAG, "${LogSource.VIEWMODEL} ${e.message}")
            } finally {
                _isLoading.value = false
                Log.i(TAG, "${LogSource.VIEWMODEL} finish load \n" +
                        "_isLoading = ${_isLoading.value}")
            }
        }
    }

    companion object {
        private const val TAG = "MainScreenViewModel"
    }
}