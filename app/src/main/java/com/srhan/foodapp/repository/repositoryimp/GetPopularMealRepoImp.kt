package com.srhan.foodapp.repository.repositoryimp

import com.srhan.foodapp.models.MealsByCategoryList
import com.srhan.foodapp.remote.FoodApiService
import com.srhan.foodapp.repository.GetPopularMealRepo
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPopularMealRepoImp @Inject constructor(private val apiService: FoodApiService) :
    GetPopularMealRepo {

    override fun getPopularMeal(categoryName: String): Flow<Resource<MealsByCategoryList>> = flow {
        emit(Resource.Loading())
        try {
            val randomMeal = apiService.getPopularMeal(categoryName)
            emit(Resource.Success(randomMeal))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))

        }

    }


}