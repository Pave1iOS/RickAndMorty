package com.example.rickandmorty.domain

import android.util.Log
import com.example.rickandmorty.data.api.Character
import com.example.rickandmorty.data.api.RickAndMortyAPI
import com.example.rickandmorty.helpers.LogSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val api: RickAndMortyAPI) {

//    suspend fun loadImage():

    suspend fun getListCharacters(): Result<List<Character>> {
        return try {
            val response = api.getListCharacters()
            Log.i(LogSource.NETWORK, "$TAG - getListCharacters = [$response]")
            Result.success(response)
        } catch (e: Exception) {
            Log.e(LogSource.NETWORK, "$TAG - ${e.message}")
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "NetworkRepository"
    }
}