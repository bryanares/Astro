package com.brian.astro.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SpaceDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSpace(space: Space)

    @Query("SELECT * FROM space_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Space>>
}