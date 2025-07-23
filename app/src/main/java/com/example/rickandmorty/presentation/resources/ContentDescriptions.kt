package com.example.rickandmorty.presentation.resources

object ContentDescriptions {

    val search = "Иконка поиска"

    fun imageDescription(name: String): String {
        return "Изображение персонажа $name"
    }

    fun statusDescription(status: String): String {
        return "Статус $status"
    }

}