package com.brian.astro.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "space_table")
data class Space(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String
)