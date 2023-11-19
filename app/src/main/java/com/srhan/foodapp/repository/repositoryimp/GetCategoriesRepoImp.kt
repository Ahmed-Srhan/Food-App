package com.srhan.foodapp.repository.repositoryimp

import com.srhan.foodapp.models.CategoryList
import com.srhan.foodapp.remote.FoodApiService
import com.srhan.foodapp.repository.GetCategoriesRepo
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoriesRepoImp @Inject constructor(private val apiService: FoodApiService) :
    GetCategoriesRepo {

    override fun getCategories(): Flow<Resource<CategoryList>> = flow {
        emit(Resource.Loading())
        try {
            val randomMeal = apiService.getCategories()
            emit(Resource.Success(randomMeal))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))

        }

    }

}