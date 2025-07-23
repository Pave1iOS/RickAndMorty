package com.example.rickandmorty.domain

import com.example.rickandmorty.data.api.AllCharacters
import com.example.rickandmorty.data.api.RickAndMortyAPI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkRepository @Inject constructor(private val api: RickAndMortyAPI) {

//    suspend fun loadImage():

    suspend fun getListCharacters(): Result<List<AllCharacters>> {
        return try {
            val response = api.getListCharacters()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}