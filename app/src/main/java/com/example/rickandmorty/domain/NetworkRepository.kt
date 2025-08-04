package com.example.rickandmorty.domain

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.utils.LogSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val api: RickAndMortyAPI) {

    fun getPagingCharacter(): Pager<Int, RickAndMortyCharacter> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CharacterPagingSource(api) }
        )
    }

    fun getFilteredCharacters(status: String?, gender: String?): Pager<Int, RickAndMortyCharacter> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CharacterPagingSource(api, status, gender) }
        )
    }

    companion object {
        private const val TAG = "NetworkRepository"
    }
}