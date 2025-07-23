package com.example.rickandmorty.data.api

import com.google.gson.annotations.SerializedName

data class Character(

    @SerializedName("name")
    val name: String,

    @SerializedName("species")
    val species: String,

    @SerializedName("status")
    val status: String,

    @SerializedName("gender")
    val gender: String,

    @SerializedName("image")
    val image: String
)