package com.example.rickandmorty.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.App
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
        val networkStatus = getNetworkStatus()

        logNetworkStatusOnce(networkStatus)

        return try {

            val characters = when(networkStatus) {

                NetworkStatus.ONLINE -> {
                    Log.i(TAG, "${LogSource.NETWORK} page: $page")

                    val response = api.getCharacters(page, status = status, gender = gender)
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

                NetworkStatus.OFFLINE -> {

                    val cached = database.rickAndMortyDao().getCharacters(status = status, gender = gender)
                    Log.i(TAG, "${LogSource.DATABASE} get: $cached")

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
            }

            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = when {
                    networkStatus == NetworkStatus.ONLINE && characters.isNotEmpty() -> page + 1
                    else -> null
                }
            )
        } catch (e: Exception) {
            Log.e(TAG, "${LogSource.NETWORK} error: ${e.message}")
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val TAG = "CharacterPagingSource"
        private var logNetworkStatus: NetworkStatus? = null

        fun logNetworkStatusOnce(currentStatus: NetworkStatus) {
            if (logNetworkStatus != currentStatus) {
                Log.i(TAG, "${LogSource.NETWORK} is ${currentStatus.name.lowercase()}")
                logNetworkStatus = currentStatus
            }
        }

        enum class NetworkStatus {
            ONLINE,
            OFFLINE
        }

        fun getNetworkStatus(): NetworkStatus {
            val context = App.instance.getAppContext()
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return NetworkStatus.OFFLINE
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return NetworkStatus.OFFLINE
            return if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                NetworkStatus.ONLINE
            } else {
                NetworkStatus.OFFLINE
            }
        }
    }
}