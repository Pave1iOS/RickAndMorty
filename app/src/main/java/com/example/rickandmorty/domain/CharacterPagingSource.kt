package com.example.rickandmorty.domain

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.database.AppDatabase
import com.example.rickandmorty.data.database.RickAndMortyEntity
import com.example.rickandmorty.utils.LogSource
import com.example.rickandmorty.utils.NetworkMonitor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterPagingSource @Inject constructor(
    private val database: AppDatabase,
    private val api: RickAndMortyAPI,
    private val name: String? = null,
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
        val pageSize = params.loadSize
        val offset = (page - 1) * pageSize
        val networkStatus = NetworkMonitor.getStatus()

        NetworkMonitor.logStatus(TAG)

        return try {

            val characters = when(networkStatus) {

                ONLINE -> {
                    Log.i(TAG, "${LogSource.NETWORK} page: $page")

                    val response = api.getCharacters(page, status = status, gender = gender, name = name)
                    val characters = response.results

                    Log.i(TAG, "${LogSource.NETWORK} data: $characters")

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

                    characters
                }

                OFFLINE -> {

                    val cached = database.rickAndMortyDao().getCharacters(
                        status = status,
                        gender = gender,
                        limit = pageSize,
                        offset = offset
                    )

                    Log.i(TAG, "${LogSource.DATABASE} get: $cached")
                    Log.i(TAG, "${LogSource.DATABASE} pageSize: $pageSize, offset: $offset")

                    cached.map {
                        RickAndMortyCharacter(
                            id = it.id,
                            name = it.name,
                            status = it.status,
                            gender = it.gender,
                            species = it.species,
                            image = it.image
                        )
                    }
                }

                else -> emptyList()
            }

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (characters.size < pageSize) null else page + 1
            )
        } catch (e: Exception) {
            Log.e(TAG, "${LogSource.NETWORK} error: ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "CharacterPagingSource"
        private val ONLINE = NetworkMonitor.NetworkStatus.ONLINE
        private val OFFLINE = NetworkMonitor.NetworkStatus.OFFLINE

    }
}