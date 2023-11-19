package com.srhan.foodapp.repository

import com.srhan.foodapp.models.MealList
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetRandomMealRepo {
    fun getRandomMeal(): Flow<Resource<MealList>>
}