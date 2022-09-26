package com.example.Healthify.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.Healthify.model.Workout2
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Workout2::class], version = 1, exportSchema = false)
abstract class WorkoutDatabase : RoomDatabase() {

    abstract val WorkoutDatabaseDao: WorkoutDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: WorkoutDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): WorkoutDatabase {
            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WorkoutDatabase::class.java,
                        "workout_history_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }


        }
    }

}