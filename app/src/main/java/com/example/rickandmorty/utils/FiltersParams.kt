package com.example.rickandmorty.utils

import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter

data class FilterParams(
    val status: StatusFilter?,
    val gender: GenderFilter?,
    val query: String
) {
    val hasFilter: Boolean
        get() = query.isNotBlank() || status != null || gender != null

    val isReset: Boolean
        get() = status == null && gender == null
}