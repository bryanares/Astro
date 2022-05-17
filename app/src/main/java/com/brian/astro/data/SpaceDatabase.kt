package com.brian.astro.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Space::class], version = 1, exportSchema = false)
abstract class SpaceDatabase: RoomDatabase() {

    abstract fun spaceDao() : SpaceDao

    companion object{
        @Volatile
        private var INSTANCE : SpaceDatabase? = null

        fun getDatabase(context: Context): SpaceDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SpaceDatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}