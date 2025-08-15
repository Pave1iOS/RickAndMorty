package com.example.rickandmorty.domain

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.rickandmorty.data.api.Episode
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.database.AppDatabase
import com.example.rickandmorty.utils.LogSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(
    private val api: RickAndMortyAPI,
    private val database: AppDatabase
) {

    suspend fun getCharacterEpisodes(character: RickAndMortyCharacter): List<Episode> {
        val episodeID = character.episode.map { it.substringAfterLast("/") }

        return if (episodeID.size == 1) {
            val episode = listOf(api.getEpisodeByID(episodeID.first()))
            Log.i(TAG, "${LogSource.NETWORK} fetching episode: $episode")

            episode
        } else {
            val episodes = api.getEpisodesByIDs(episodeID.joinToString(","))
            Log.i(TAG, "${LogSource.NETWORK} fetching episodes: $episodes")

            episodes
        }
    }

    suspend fun fetchCharacterById(id: Int): RickAndMortyCharacter {
        Log.i(TAG, "${LogSource.NETWORK} fetching character with id = $id")
        return api.getCharacterById(id)
    }

    fun fetchCharacters(): Flow<PagingData<RickAndMortyCharacter>> {

        Log.i(TAG, "${LogSource.NETWORK} fetching all characters")

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                CharacterPagingSource(
                    api = api,
                    database = database
                )
            }
        ).flow
    }

    fun fetchFilteredCharacters(status: String?, gender: String?, name: String?): Flow<PagingData<RickAndMortyCharacter>> {

        Log.i(TAG, "${LogSource.NETWORK} fetching filter characters by \n" +
                "- status: $status \n" +
                "- gender: $gender \n" +
                "- name: ${name?.ifBlank { "empty" }}")

        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                CharacterPagingSource(
                    database = database,
                    api = api,
                    status= status,
                    gender = gender,
                    name = name
                )
            }
        ).flow
    }

    companion object {
        private const val TAG = "NetworkRepository"
        private const val NETWORK_PAGE_SIZE = 20
    }
}