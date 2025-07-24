package com.example.rickandmorty.domain

import android.util.Log
import com.example.rickandmorty.data.api.Character
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.utils.LogSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val api: RickAndMortyAPI) {

    suspend fun getListCharacters(): Result<List<Character>> {
        return try {
            val response = api.getCharacters()
            Log.i(TAG, "${LogSource.NETWORK} - getListCharacters = [$response]")
            Result.success(response.results)
        } catch (e: Exception) {
            Log.e(TAG, "${LogSource.NETWORK} - ${e.message}")
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "NetworkRepository"
    }
}