package com.example.rickandmorty.data.database

import androidx.room.TypeConverter
import com.example.rickandmorty.data.api.params.CharacterGender
import com.example.rickandmorty.data.api.params.CharacterStatus

class DatabaseConverters {
    @TypeConverter
    fun fromStatus(value: CharacterStatus): String = value.name

    @TypeConverter
    fun toStatus(value: String): CharacterStatus = CharacterStatus.valueOf(value)

    @TypeConverter
    fun fromGender(value: CharacterGender): String = value.name

    @TypeConverter
    fun toGender(value: String): CharacterGender = CharacterGender.valueOf(value)
}