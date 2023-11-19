package com.srhan.foodapp.repository.repositoryimp

import com.srhan.foodapp.models.MealList
import com.srhan.foodapp.remote.FoodApiService
import com.srhan.foodapp.repository.GetRandomMealRepo
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRandomMealRepoImp @Inject constructor(private val apiService: FoodApiService) :
    GetRandomMealRepo {
    override fun getRandomMeal(): Flow<Resource<MealList>> = flow {
        emit(Resource.Loading())
        try {
            val randomMeal = apiService.getRandomMeal()
            emit(Resource.Success(randomMeal))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))

        }

    }

}