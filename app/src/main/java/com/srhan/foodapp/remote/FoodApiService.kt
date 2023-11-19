package com.srhan.foodapp.remote

import com.srhan.foodapp.models.CategoryList
import com.srhan.foodapp.models.MealList
import com.srhan.foodapp.models.MealsByCategoryList
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApiService {
    @GET("random.php")
    suspend fun getRandomMeal(): MealList

    @GET("lookup.php")
    suspend fun getMealInfo(
        @Query("i")
        i: String
    ): MealList

    @GET("filter.php")
    suspend fun getPopularMeal(
        @Query("c")
        categoryName: String
    ): MealsByCategoryList

    @GET("categories.php")
    suspend fun getCategories(): CategoryList

    @GET("filter.php")
    suspend fun getMealByCategory(
        @Query("c")
        categoryName: String
    ): MealsByCategoryList


    @GET("search.php")
    suspend fun searchMeals(
        @Query("s")
        querySearch: String
    ): MealList

}