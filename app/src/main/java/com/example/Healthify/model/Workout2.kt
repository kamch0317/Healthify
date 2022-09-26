package com.example.Healthify.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

var workoutList2 = mutableListOf<Workout2>()

val WORK_ID_EXTRA = "workExtra"

@Parcelize
@Entity(tableName = "workout_data_table")

data class Workout2(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "work_date")
    var workoutDate: String,

    @ColumnInfo(name = "work_time")
    var workoutTime: String,

    @ColumnInfo(name = "work_name")
    var workoutName: String,

    @ColumnInfo(name = "work_sets")
    var workoutSets: String

) : Parcelable
