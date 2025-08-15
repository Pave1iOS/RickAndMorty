package com.example.rickandmorty.presentation.screens.detailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.Episode
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.domain.NetworkRepository
import com.example.rickandmorty.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailsState())
    val state: StateFlow<CharacterDetailsState> = _state

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    fun loadCharacter(id: Int) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val character = repository.fetchCharacterById(id)
                val episodes = repository.getCharacterEpisodes(character)

                _state.value = CharacterDetailsState(
                    character = character,
                    episodes = episodes,
                    isLoading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(isLoading = false, error = e.localizedMessage)
                _uiEvent.send(UiEvent.ShowMessage(R.string.error_loading_character))
            }
        }
    }
}

data class CharacterDetailsState(
    val character: RickAndMortyCharacter? = null,
    val episodes: List<Episode> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
