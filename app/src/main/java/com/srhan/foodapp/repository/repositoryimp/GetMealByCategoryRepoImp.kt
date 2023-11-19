package com.srhan.foodapp.repository.repositoryimp

import com.srhan.foodapp.models.MealsByCategoryList
import com.srhan.foodapp.remote.FoodApiService
import com.srhan.foodapp.repository.GetMealByCategoryRepo
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetMealByCategoryRepoImp @Inject constructor(private val apiService: FoodApiService) :
    GetMealByCategoryRepo {

    override fun getMealByCategory(categoryName: String): Flow<Resource<MealsByCategoryList>> =
        flow {
            emit(Resource.Loading())
            try {
                val randomMeal = apiService.getMealByCategory(categoryName)
                emit(Resource.Success(randomMeal))

            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))

            }

        }


}