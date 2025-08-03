package com.example.rickandmorty.data.api.params

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CharacterGender(val displayName: String) {

    @SerialName("Female")
    FEMALE("Female"),

    @SerialName("Male")
    MALE("Male"),

    @SerialName("Genderless")
    GENDERLESS("Genderless"),

    @SerialName("unknown")
    UNKNOWN("unknown")
}