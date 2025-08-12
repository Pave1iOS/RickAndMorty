package com.example.rickandmorty.data.api

import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter
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
data class Origin(
    val name: String,
    val url: String
)

@Serializable
data class Location(
    val name: String,
    val url: String
)

@Serializable
data class RickAndMortyCharacter(
    val id: Int,
    val name: String,
    val status: StatusFilter,
    val gender: GenderFilter,
    val species: String,
    val type: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

