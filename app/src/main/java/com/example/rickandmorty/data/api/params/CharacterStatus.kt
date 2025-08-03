package com.example.rickandmorty.data.api.params

import com.example.rickandmorty.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CharacterStatus(val colorResID: Int) {

    @SerialName("Alive")
    ALIVE(R.color.green_circle),

    @SerialName("Dead")
    DEAD(R.color.red_circle),

    @SerialName("unknown")
    UNKNOWN(R.color.yellow_circle)
}
