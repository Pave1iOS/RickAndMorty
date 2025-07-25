package com.example.rickandmorty.domain

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmorty.data.api.RickAndMortyCharacter
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.utils.LogSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterPagingSource @Inject constructor(private val api: RickAndMortyAPI)
    : PagingSource<Int, RickAndMortyCharacter>() {

    override fun getRefreshKey(state: PagingState<Int, RickAndMortyCharacter>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RickAndMortyCharacter> {
        val page = params.key ?: 1

        Log.i(TAG, "${LogSource.NETWORK} page: $page")

        return try {
            val response = api.getCharacters(page)
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