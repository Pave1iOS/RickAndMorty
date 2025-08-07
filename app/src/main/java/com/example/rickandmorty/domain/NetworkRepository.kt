package com.example.rickandmorty.domain

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.RemoteMediator
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus
import com.example.rickandmorty.data.database.AppDatabase
import com.example.rickandmorty.data.database.RickAndMortyEntity
import com.example.rickandmorty.data.database.RickAndMortyRemoteMediator
import com.example.rickandmorty.utils.LogSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val database: AppDatabase,
    private val api: RickAndMortyAPI
) {

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

    @OptIn(ExperimentalPagingApi::class)
    fun getRickAndMortyCharacters(): Flow<PagingData<RickAndMortyEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = RickAndMortyRemoteMediator(database, api),
            pagingSourceFactory = { database.rickAndMortyDao().getCharacters() }
        ).flow
    }

    companion object {
        private const val TAG = "NetworkRepository"
    }
}