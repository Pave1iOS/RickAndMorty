package com.example.rickandmorty.data.api.params

enum class CharacterGender(val value: String) {

    FEMALE("female"),
    MALE("male"),
    GENDERLESS("genderless"),
    UNKNOWN("unknown");

    companion object {
        fun from(value: String): CharacterGender {
            return entries.find { it.value == value } ?: UNKNOWN
        }
    }
}