package com.example.rickandmorty.domain

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.database.AppDatabase
import com.example.rickandmorty.data.database.RickAndMortyEntity
import com.example.rickandmorty.utils.LogSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val api: RickAndMortyAPI,
    private val database: AppDatabase
) {

    fun fetchCharacters(): Pager<Int, RickAndMortyCharacter> {

        Log.i(TAG, "${LogSource.NETWORK} fetching all characters")

        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                CharacterPagingSource(
                    api = api,
                    database = database
                )
            }
        )
    }

    fun getFilteredCharacters(status: String?, gender: String?): Pager<Int, RickAndMortyCharacter> {

        Log.i(TAG, "${LogSource.NETWORK} fetching filter characters by status: $status, gender: $gender")

        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                CharacterPagingSource(
                    database = database,
                    api = api,
                    status= status,
                    gender = gender
                )
            }
        )
    }

    companion object {
        private const val TAG = "NetworkRepository"
    }
}