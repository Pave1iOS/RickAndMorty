package com.example.rickandmorty.domain

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.database.AppDatabase
import com.example.rickandmorty.data.database.RickAndMortyEntity
import com.example.rickandmorty.utils.LogSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterPagingSource @Inject constructor(
    private val database: AppDatabase,
    private val api: RickAndMortyAPI,
    private val status: String? = null,
    private val gender: String? = null
) : PagingSource<Int, RickAndMortyCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, RickAndMortyCharacter>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickAndMortyCharacter> {
        val page = params.key ?: 1

        Log.i(TAG, "${LogSource.NETWORK} page: $page")

        return try {
            val response = api.getCharacters(page, status = status, gender = gender)
            val characters = response.results

            Log.i(TAG, "${LogSource.NETWORK} getting: $characters")

            val entities = characters.map {
                RickAndMortyEntity(
                    id = it.id,
                    name = it.name,
                    status = it.status,
                    gender = it.gender,
                    species = it.species,
                    image = it.image
                )
            }

            database.rickAndMortyDao().insertAll(entities)

            Log.i(TAG, "${LogSource.DATABASE} insert: $entities")

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )
        } catch (e: Exception) {
            Log.e(TAG, "${LogSource.NETWORK} error: ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "CharacterPagingSource"
    }
}