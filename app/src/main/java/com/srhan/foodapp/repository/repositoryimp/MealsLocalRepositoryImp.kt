package com.srhan.foodapp.repository.repositoryimp

import com.srhan.foodapp.db.MealsDao
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.repository.MealsLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MealsLocalRepositoryImp @Inject constructor(private val mealsDao: MealsDao) :
    MealsLocalRepository {
    override suspend fun upsert(meal: Meal) = mealsDao.upsert(meal)
    override suspend fun delete(meal: Meal) = mealsDao.delete(meal)

    override fun getAllFavouriteMeals(): Flow<List<Meal>> = mealsDao.getAllMeals()
}