package com.example.rickandmorty.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.data.api.Location
import com.example.rickandmorty.data.api.Origin
import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter

@Entity(tableName = "characters")
data class RickAndMortyEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val species: String,
    val type: String,
    val status: StatusFilter,
    val gender: GenderFilter,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
