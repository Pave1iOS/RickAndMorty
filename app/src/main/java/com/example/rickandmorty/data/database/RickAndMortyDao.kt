package com.example.rickandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RickAndMortyDao {
    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, RickAndMortyCharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<RickAndMortyCharacterEntity>)

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}