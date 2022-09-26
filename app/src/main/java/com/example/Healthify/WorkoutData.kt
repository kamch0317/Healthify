package com.example.Healthify

var workoutList = mutableListOf<WorkoutData>()

class WorkoutData(

    var pic: Int,

    var title: String,

    var cmore: String,

    val id: Int? = workoutList.size

)