package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.api.params.CharacterStatus
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info,

    @SerializedName("results")
    val results: List<RickAndMortyCharacter>
)

data class Info(
    @SerializedName("count")
    val count: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("prev")
    val prev: String?
)

data class RickAndMortyCharacter(

    @SerializedName("name")
    val name: String,
    @SerializedName("species")
    val species: String,
    @SerializedName("status")
    val status: CharacterStatus,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("image")
    val image: String
)