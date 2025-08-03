package com.example.rickandmorty.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class StatusAdapter : JsonDeserializer<CharacterStatus> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): CharacterStatus {
        return CharacterStatus.from(json.asString)
    }
}