package com.brian.astro.data

import androidx.lifecycle.LiveData

class SpaceRepository(private val spaceDao: SpaceDao) {

    val readAllData: LiveData<List<Space>> = spaceDao.readAllData()

    suspend fun addSpace(space: Space){
        spaceDao.addSpace(space)
    }
}