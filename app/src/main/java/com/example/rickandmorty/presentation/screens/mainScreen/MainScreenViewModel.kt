package com.example.rickandmorty.presentation.screens.mainScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.domain.NetworkRepository
import com.example.rickandmorty.utils.LogSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    repository: NetworkRepository
): ViewModel() {

    val charactersPagingFlow: Flow<PagingData<RickAndMortyCharacter>> =
        repository.getPagingCharacter()
            .flow
            .cachedIn(viewModelScope)

    companion object {
        private const val TAG = "MainScreenViewModel"
    }
}