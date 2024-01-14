package com.groups.BrainTrainApp.Components.Common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groups.BrainTrainApp.Enum.Level

class LevelViewModel: ViewModel(){
    private val mutableSelectedItem = MutableLiveData<Level>()
    val selectedItem: LiveData<Level> get() = mutableSelectedItem
    fun selectItem(item: Level) {
        mutableSelectedItem.value = item
    }
}