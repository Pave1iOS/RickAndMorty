package com.example.rickandmorty.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus

@Entity(tableName = "characters")
data class RickAndMortyCharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val name: String,
    val species: String,
    val status: CharacterStatus,
    val gender: CharacterGender,
    val image: String
)
