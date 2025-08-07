package com.example.rickandmorty.data.database

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.api.RickAndMortyAPI
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val api: RickAndMortyAPI,
    private val status: String? = null,
    private val gender: String? = null
) : RemoteMediator<Int, RickAndMortyEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RickAndMortyEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                (state.pages.size + 1)
            }
        }

        return try {
            val response = api.getCharacters(page, status, gender)
            val entities = response.results.map {
                RickAndMortyEntity(
                    id = it.id,
                    name = it.name,
                    status = it.status,
                    species = it.species,
                    gender = it.gender,
                    image = it.image
                )
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.rickAndMortyDao().clearCharacters()
                }
                database.rickAndMortyDao().insertCharacters(entities)
            }

            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (e: Exception) {
            Log.e("CharacterRemoteMediator", "Error: ${e.message}")
            MediatorResult.Error(e)
        }
    }
}
