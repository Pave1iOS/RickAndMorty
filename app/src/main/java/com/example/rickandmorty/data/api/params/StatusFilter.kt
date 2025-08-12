package com.example.rickandmorty.data.api.params

import com.example.rickandmorty.R
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class StatusFilter(val colorResID: Int, val displayName: String) {

    @SerialName("Alive")
    ALIVE(R.color.green_circle, "Alive"),

    @SerialName("Dead")
    DEAD(R.color.red_circle, "Dead"),

    @SerialName("unknown")
    UNKNOWN(R.color.yellow_circle, "Unknown")
}
