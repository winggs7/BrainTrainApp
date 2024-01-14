package com.groups.BrainTrainApp.Components.Common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groups.BrainTrainApp.Enum.Level

class LevelViewModel: ViewModel(){
    private val mutableSelectedLevel = MutableLiveData<Level>()
    val selectedLevel: LiveData<Level> get() = mutableSelectedLevel
    fun selectLevel(level: Level) {
        mutableSelectedLevel.value = level
    }
}