package com.example.rickandmorty.presentation.resources

object ContentDescriptions {

    fun imageDescription(name: String): String {
        return "Изображение персонажа $name"
    }

    fun statusDescription(status: String): String {
        return "Статус $status"
    }

}