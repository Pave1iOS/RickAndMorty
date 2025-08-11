package com.example.rickandmorty.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [RickAndMortyEntity::class], version = 2)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun rickAndMortyDao(): RickAndMortyDao
}