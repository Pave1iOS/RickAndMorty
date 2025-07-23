package com.example.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.api.Character
import com.example.rickandmorty.domain.NetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class RickAndMortyViewModel @Inject constructor(
    private val repository: NetworkRepository
): ViewModel() {

    private val _characters = MutableStateFlow<Result<List<Character>>?>(null)
    val characters: StateFlow<Result<List<Character>>?> = _characters

    fun getAllCharacters() {

        viewModelScope.launch {
            _characters.value = null
            _characters.value = repository.getListCharacters()
        }
    }
}