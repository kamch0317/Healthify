package com.example.Healthify

var workoutDetails = mutableListOf<WorkoutDataDetails>()

class WorkoutDataDetails (

    var picDetails: Int,

    var nameDetails: String,

    var desDetails: String,

    var link: String?,

    val idDetails: Int? = workoutDetails.size
)