package com.example.rickandmorty.data.database

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmorty.data.api.RickAndMortyAPI
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class RickAndMortyRemoteMediator @Inject constructor (
    private val database: AppDatabase,
    private val api: RickAndMortyAPI
) : RemoteMediator<Int, RickAndMortyEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RickAndMortyEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.APPEND -> state.pages.size + 1
            LoadType.PREPEND -> return MediatorResult.Success(true)
        }

        return try {
            val response = api.getCharacters(page)
            val entities = response.results.map {
                RickAndMortyEntity(
                    id = it.id,
                    name = it.name,
                    species = it.species,
                    status = it.status,
                    gender = it.gender,
                    image = it.image
                )
            }

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.rickAndMortyDao().clearAll()
                }
                database.rickAndMortyDao().insertAll(entities)
            }

            MediatorResult.Success(endOfPaginationReached = response.info.next == null)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
