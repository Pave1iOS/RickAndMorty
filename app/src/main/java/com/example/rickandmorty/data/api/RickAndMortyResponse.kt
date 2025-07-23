package com.example.rickandmorty.data.api

import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: ByteArray
)