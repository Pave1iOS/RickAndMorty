package com.example.rickandmorty.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RickAndMortyDao {

    @Query("SELECT * FROM characters WHERE (:status IS NULL OR status = :status) AND (:gender IS NULL OR gender = :gender)")
    fun getCachedCharacters(status: String?, gender: String?): List<RickAndMortyEntity>

    @Query("SELECT * FROM characters WHERE (:status IS NULL OR status = :status) AND (:gender IS NULL OR gender = :gender)")
    fun getCharacters(status: String?, gender: String?): PagingSource<Int, RickAndMortyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<RickAndMortyEntity>)

    @Query("DELETE FROM characters WHERE (:status IS NULL OR status = :status) AND (:gender IS NULL OR gender = :gender)")
    suspend fun clearFiltered(status: String?, gender: String?)
}

