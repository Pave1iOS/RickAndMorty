package com.example.rickandmorty.presentation.screens.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.domain.NetworkRepository
import com.example.rickandmorty.utils.LogSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val repository: NetworkRepository
): ViewModel() {

    private val _filteredCharacters = MutableStateFlow<PagingData<RickAndMortyCharacter>>(PagingData.empty())
    val filteredCharacters: StateFlow<PagingData<RickAndMortyCharacter>> = _filteredCharacters

    private val _isFiltered = MutableStateFlow(false)
    val isFiltered: StateFlow<Boolean> = _isFiltered


    val charactersPagingFlow: Flow<PagingData<RickAndMortyCharacter>> =
        repository.getPagingCharacter()
            .flow
            .cachedIn(viewModelScope)

    fun filterCharacters(status: CharacterStatus?, gender: CharacterGender?) {

        _isFiltered.value = true

        val statusString = status?.displayName
        val genderString = gender?.displayName

        viewModelScope.launch {
            repository.getFilteredCharacters(statusString, genderString)
                .flow
                .cachedIn(viewModelScope)
                .collect {
                    _filteredCharacters.value = it
                    Log.i(TAG, "${LogSource.VIEWMODEL} status: $status, gender: $gender")
                }
        }
    }

    fun clearFilter() {
        _isFiltered.value = false
    }


    companion object {
        private const val TAG = "MainScreenViewModel"
    }
}