package com.example.rickandmorty.utils

import com.example.rickandmorty.R

enum class CharacterStatus(val value: String, val colorResID: Int) {
    ALIVE("alive", R.color.green_circle),
    DEAD("dead", R.color.red_circle),
    UNKNOWN("unknown", R.color.yellow_circle);

    companion object {
        fun from(value: String): CharacterStatus {
            return entries.find { it.value.equals(value, ignoreCase = true) } ?: UNKNOWN
        }
    }
}