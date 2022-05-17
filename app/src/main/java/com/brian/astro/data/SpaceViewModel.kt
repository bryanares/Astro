package com.brian.astro.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SpaceViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Space>>
    private val repository: SpaceRepository

    init {
        val spaceDao = SpaceDatabase.getDatabase(application).spaceDao()
        repository = SpaceRepository(spaceDao)
        readAllData = repository.readAllData
    }

    fun addSpace(space: Space){
        viewModelScope.launch (Dispatchers.IO){
            repository.addSpace(space)
        }
    }
}