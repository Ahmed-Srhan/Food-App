package com.srhan.foodapp.repository.repositoryimp

import com.srhan.foodapp.models.MealList
import com.srhan.foodapp.remote.FoodApiService
import com.srhan.foodapp.repository.SearchMealRepo
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMealRepoImp @Inject constructor(private val apiService: FoodApiService) :
    SearchMealRepo {

    override fun searchMeal(querySearch: String): Flow<Resource<MealList>> = flow {
        emit(Resource.Loading())
        try {
            val randomMeal = apiService.searchMeals(querySearch)

            emit(Resource.Success(randomMeal))

        } catch (e: Exception) {
            throw e // Rethrow the exception to trigger the catch block
        }
    }.catch { e ->
        emit(Resource.Error(e.message ?: "Unknown error occurred"))
    }

}


