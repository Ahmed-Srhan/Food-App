package com.srhan.foodapp.repository

import com.srhan.foodapp.models.CategoryList
import com.srhan.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface GetCategoriesRepo {
    fun getCategories(): Flow<Resource<CategoryList>>
}