package com.example.rickandmorty.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character")
    suspend fun getCharacters( @Query("page") page: Int): CharacterResponse

}