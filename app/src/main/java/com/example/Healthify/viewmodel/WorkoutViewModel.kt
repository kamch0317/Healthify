package com.example.Healthify.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.Healthify.database.WorkoutDatabase
import com.example.Healthify.repository.WorkoutRepository
import com.example.Healthify.model.Workout2
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    val getAllData: LiveData<List<Workout2>>
    private val repository: WorkoutRepository

    init {
        val workoutDatabaseDao = WorkoutDatabase.getInstance(application).WorkoutDatabaseDao
        repository = WorkoutRepository(workoutDatabaseDao)
        getAllData = repository.getAllData
    }

    fun addWorkout2(workout: Workout2) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWorkout2(workout)
        }
    }

    fun updateWorkout2(workout: Workout2) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWorkout2(workout)
        }
    }

    fun deleteWorkout2(workout: Workout2) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWorkout2(workout)
        }
    }

    fun deleteAllWorkout2() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllWorkout2()
        }
    }
}