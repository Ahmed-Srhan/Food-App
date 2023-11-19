package com.srhan.foodapp.repository

import com.srhan.foodapp.models.MealsByCategoryList
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetMealByCategoryRepo {
    fun getMealByCategory(categoryName: String): Flow<Resource<MealsByCategoryList>>
}