package com.example.rickandmorty.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RickAndMortyDao {

    @Query(""" 
        SELECT * FROM characters 
        WHERE (:status IS NULL OR status = :status) 
        AND (:gender IS NULL OR gender = :gender) 
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getCharacters(
        status: String?,
        gender: String?,
        limit: Int,
        offset: Int
    ): List<RickAndMortyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<RickAndMortyEntity>)

    @Query("DELETE FROM characters WHERE (:status IS NULL OR status = :status) AND (:gender IS NULL OR gender = :gender)")
    suspend fun clearFiltered(status: String?, gender: String?)
}

