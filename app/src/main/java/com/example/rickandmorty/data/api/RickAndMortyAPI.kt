package com.example.rickandmorty.data.api

import retrofit2.http.GET

interface RickAndMortyAPI {

    @GET("character")
    suspend fun getListCharacters(): List<Character>

}