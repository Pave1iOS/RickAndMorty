package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    @SerialName("info")
    val info: Info,

    @SerialName("results")
    val results: List<RickAndMortyCharacter>
)

@Serializable
data class Info(
    @SerialName("count")
    val count: Int,
    @SerialName("pages")
    val pages: Int,
    @SerialName("next")
    val next: String? = null,
    @SerialName("prev")
    val prev: String? = null
)

@Serializable
data class RickAndMortyCharacter(
    @SerialName("name")
    val name: String,
    @SerialName("species")
    val species: String,
    @SerialName("status")
    val status: CharacterStatus,
    @SerialName("gender")
    val gender: CharacterGender,
    @SerialName("image")
    val image: String
)
