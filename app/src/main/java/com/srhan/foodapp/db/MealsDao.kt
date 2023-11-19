package com.srhan.foodapp.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.srhan.foodapp.models.Meal
import kotlinx.coroutines.flow.Flow

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meal: Meal)

    @Delete
    suspend fun delete(meal: Meal)

    @Query("SELECT * FROM meals_table")
    fun getAllMeals(): Flow<List<Meal>>

}