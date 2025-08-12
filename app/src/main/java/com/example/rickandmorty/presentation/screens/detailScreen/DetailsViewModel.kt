package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.domain.NetworkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> = _state

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val character = repository.fetchCharacterById(id)
                _state.value = CharacterDetailsState(character = character, isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
            }
        }
    }
}

data class CharacterDetailsState(
    val character: RickAndMortyCharacter? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)