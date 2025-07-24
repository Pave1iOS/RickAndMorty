package com.example.rickandmorty.utils

object ContentDescriptions {

    const val search = "Иконка поиска"
    const val filter = "Кнопка фильтра"

    fun imageDescription(name: String): String {
        return "Изображение персонажа $name"
    }

    fun statusDescription(status: String): String {
        return "Статус $status"
    }

}