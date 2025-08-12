package com.example.rickandmorty.data.database

import androidx.room.TypeConverter
import com.example.rickandmorty.data.api.Location
import com.example.rickandmorty.data.api.Origin
import com.example.rickandmorty.data.api.params.GenderFilter
import com.example.rickandmorty.data.api.params.StatusFilter
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class DatabaseConverters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStatus(value: StatusFilter): String = value.name

    @TypeConverter
    fun toStatus(value: String): StatusFilter = StatusFilter.valueOf(value)

    @TypeConverter
    fun fromGender(value: GenderFilter): String = value.name

    @TypeConverter
    fun toGender(value: String): GenderFilter = GenderFilter.valueOf(value)

    @TypeConverter
    fun originToString(origin: Origin): String =
        json.encodeToString(Origin.serializer(), origin)

    @TypeConverter
    fun stringToOrigin(data: String): Origin =
        json.decodeFromString(Origin.serializer(), data)

    @TypeConverter
    fun locationToString(location: Location): String =
        json.encodeToString(Location.serializer(), location)

    @TypeConverter
    fun stringToLocation(data: String): Location =
        json.decodeFromString(Location.serializer(), data)

    @TypeConverter
    fun listToString(list: List<String>): String =
        json.encodeToString(ListSerializer(String.serializer()), list)

    @TypeConverter
    fun stringToList(data: String): List<String> =
        json.decodeFromString(ListSerializer(String.serializer()), data)
}