package com.example.rickandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RickAndMortyDao {
    @Query("SELECT * FROM characters")
    fun getCharacters(): PagingSource<Int, RickAndMortyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<RickAndMortyEntity>)

    @Query("DELETE FROM characters")
    suspend fun clearAll()
}
