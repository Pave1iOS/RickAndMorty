package com.example.rickandmorty.utils

object ContentDescriptions {

    const val SEARCH = "Иконка поиска"
    const val FILTER = "Кнопка фильтра"
    const val SAD_MORTY = "Грустный Морти"

    fun imageDescription(name: String): String {
        return "Изображение персонажа $name"
    }

    fun statusDescription(status: String): String {
        return "Статус $status"
    }

}