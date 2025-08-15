package com.example.rickandmorty.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyAPI {

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): RickAndMortyCharacter

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("gender") gender: String? = null
    ): CharacterResponse

    @GET("episode/{ids}")
    suspend fun getEpisodesByIDs(@Path("ids") ids: String): List<Episode>

    @GET("episode/{id}")
    suspend fun getEpisodeByID(@Path("id") id: String): Episode
}