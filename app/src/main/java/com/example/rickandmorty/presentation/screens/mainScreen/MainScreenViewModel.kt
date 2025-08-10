package com.example.rickandmorty.presentation.screens.mainScreen

import android.app.Application
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
import com.example.rickandmorty.utils.NetworkMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MainScreenViewModel @Inject constructor(
    private val repository: NetworkRepository
): ViewModel() {

    private val _statusFilter = MutableStateFlow<CharacterStatus?>(null)
    private val _genderFilter = MutableStateFlow<CharacterGender?>(null)
    private val _searchQuery = MutableStateFlow("")

    private val _isFiltered = MutableStateFlow(false)

    val searchQuery: StateFlow<String> = _searchQuery

    val networkStatus: StateFlow<NetworkMonitor.NetworkStatus> =
        NetworkMonitor.getStatusFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = NetworkMonitor.NetworkStatus.ONLINE
        )

    val characters =
        combine(
            _statusFilter,
            _genderFilter,
            _searchQuery.debounce(1000)
        ) { status, gender, query ->
            Triple(status, gender, query)
        }
            .flatMapLatest { (status, gender, query) ->
                val hasFilter = status != null || gender != null || query.isNotBlank()
                _isFiltered.value = hasFilter

                Log.i(TAG, "${LogSource.VIEWMODEL} fetching characters: \n" +
                        "status = $status \n"+
                        "gender = $gender \n" +
                        "query = ${query.ifBlank { "empty" }}"
                )

                if (hasFilter) {
                    repository.fetchFilteredCharacters(status?.name, gender?.name, query)
                } else {
                    repository.fetchCharacters()
                }
            }
            .cachedIn(viewModelScope)


    fun setFilters(status: CharacterStatus?, gender: CharacterGender?) {
        _statusFilter.value = status
        _genderFilter.value = gender
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearFilter() {
        _isFiltered.value = false
        _statusFilter.value = null
        _genderFilter.value = null
        _searchQuery.value = ""
    }

    companion object {
        private const val TAG = "MainScreenViewModel"
    }
}