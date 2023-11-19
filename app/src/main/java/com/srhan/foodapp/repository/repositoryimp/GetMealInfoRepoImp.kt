package com.srhan.foodapp.repository.repositoryimp

import com.srhan.foodapp.models.MealList
import com.srhan.foodapp.remote.FoodApiService
import com.srhan.foodapp.repository.GetMealInfoRepo
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMealInfoRepoImp @Inject constructor(private val apiService: FoodApiService) :
    GetMealInfoRepo {

    override suspend fun getMealInfo(mealId: String): Flow<Resource<MealList>> = flow {
        emit(Resource.Loading())
        try {
            val randomMeal = apiService.getMealInfo(mealId)
            emit(Resource.Success(randomMeal))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))

        }

    }


}