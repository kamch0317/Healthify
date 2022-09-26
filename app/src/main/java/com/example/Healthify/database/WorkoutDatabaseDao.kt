package com.example.Healthify.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.Healthify.model.Workout2


@Dao
interface WorkoutDatabaseDao {

    @Insert
    suspend fun insert(work: Workout2)

    @Update
    suspend fun update(work: Workout2)

    @Delete
    suspend fun delete(work: Workout2)

    @Query("Select * from workout_data_table WHERE id = :key")
    suspend fun get(key: Long): Workout2?

    @Query("DELETE FROM workout_data_table")
    suspend fun deleteAllWorkout()

    @Query("SELECT * FROM workout_data_table ORDER BY id ASC")
    fun getAllData(): LiveData<List<Workout2>>


    @Query("SELECT * FROM workout_data_table ORDER BY id DESC LIMIT 1")
    suspend fun getTonight(): Workout2?
}
