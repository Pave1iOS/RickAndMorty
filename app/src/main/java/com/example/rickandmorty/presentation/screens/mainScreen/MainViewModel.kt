package com.example.rickandmorty.presentation.screens.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.rickandmorty.R
import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter
import com.example.rickandmorty.domain.NetworkRepository
import com.example.rickandmorty.utils.FilterParams
import com.example.rickandmorty.utils.NetworkMonitor
import com.example.rickandmorty.utils.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: NetworkRepository
) : ViewModel() {

    private val _statusFilter = MutableStateFlow<StatusFilter?>(null)
    private val _genderFilter = MutableStateFlow<GenderFilter?>(null)
    val _searchQuery = MutableStateFlow("")
    val isFiltered = MutableStateFlow(false)

    private val _uiEvent = Channel<UiEvent>(Channel.BUFFERED)
    val uiEvent = _uiEvent.receiveAsFlow()

    private val filters = combine(
        _statusFilter,
        _genderFilter,
        _searchQuery.debounce(1000)
    ) { status, gender, query ->
        FilterParams(status, gender, query)
    }

    val characters = filters
        .flatMapLatest { params ->
            isFiltered.value = params.hasFilter
            if (params.hasFilter) {
                repository.fetchFilteredCharacters(
                    params.status?.name,
                    params.gender?.name,
                    params.query
                )
            } else {
                repository.fetchCharacters()
            }
        }
        .cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            var wasOffline = false

            NetworkMonitor.getStatusFlow().collect { status ->
                when (status) {
                    NetworkMonitor.NetworkStatus.ONLINE -> {
                        if (wasOffline) {
                            _uiEvent.send(UiEvent.ShowMessage(R.string.online_message))
                            wasOffline = false
                        }
                    }
                    NetworkMonitor.NetworkStatus.OFFLINE -> {
                        _uiEvent.send(UiEvent.ShowMessage(R.string.offline_message))
                        wasOffline = true
                    }
                }
            }
        }
    }


    fun resetFilters() {
        _statusFilter.value = null
        _genderFilter.value = null
        _searchQuery.value = ""
    }

    fun setStatusFilter(status: StatusFilter?) { _statusFilter.value = status }
    fun setGenderFilter(gender: GenderFilter?) { _genderFilter.value = gender }
    fun setSearchQuery(query: String) { _searchQuery.value = query }
}
