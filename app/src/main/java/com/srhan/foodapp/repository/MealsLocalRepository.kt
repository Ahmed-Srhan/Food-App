package com.srhan.foodapp.repository

import com.srhan.foodapp.models.Meal
import kotlinx.coroutines.flow.Flow

interface MealsLocalRepository {
    suspend fun upsert(meal: Meal)

    suspend fun delete(meal: Meal)

    fun getAllFavouriteMeals(): Flow<List<Meal>>

}