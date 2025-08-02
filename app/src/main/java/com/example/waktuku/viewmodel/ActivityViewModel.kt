package com.example.waktuku.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.waktuku.data.model.ActivityModel
import com.example.waktuku.repository.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val repository: ActivityRepository
): ViewModel() {
    private val _activityList = MutableLiveData<List<ActivityModel>>()
    val activityList: LiveData<List<ActivityModel>> = _activityList

    init {
        loadActivity()
    }

    fun loadActivity(){
        viewModelScope.launch {
            _activityList.value = repository.getAllActivity()
        }
    }

    fun insertActivity(activity: ActivityModel){
        viewModelScope.launch {
            repository.insertActivity(activity)
            loadActivity()
        }
    }

    suspend fun insertActivityAndReturnId(activity: ActivityModel): Long {
        return repository.insertActivity(activity)
    }


    fun updateActivity(activity: ActivityModel){
        viewModelScope.launch {
            repository.updateActivity(activity)
            loadActivity()
        }
    }

    fun deleteActivity(activity: ActivityModel){
        viewModelScope.launch {
            repository.deleteActivity(activity)
            loadActivity()
        }
    }
}