package com.srhan.foodapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.srhan.foodapp.models.Meal

@Database(entities = [Meal::class], version = 1)
@TypeConverters(MealsTypeConverters::class)
abstract class MealsDatabase : RoomDatabase() {

    abstract fun mealsDao(): MealsDao
}