package com.srhan.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.repository.GetMealInfoRepo
import com.srhan.foodapp.repository.MealsLocalRepository
import com.srhan.foodapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getMealInfoRepo: GetMealInfoRepo,
    private val localRepository: MealsLocalRepository
) : ViewModel() {

    private val _mealDetail: MutableStateFlow<Resource<Meal>?> = MutableStateFlow(null)
    val mealDetail: StateFlow<Resource<Meal>?> = _mealDetail

    private val _mealDetailById: MutableStateFlow<Resource<Meal>?> = MutableStateFlow(null)
    val mealDetailById: StateFlow<Resource<Meal>?> = _mealDetailById


    fun getMealDetail(mealID: String) = viewModelScope.launch {
        getMealInfoRepo.getMealInfo(mealID).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val meal = result.data?.meals?.get(0)
                    _mealDetail.value = Resource.Success(meal!!)
                }

                is Resource.Error -> {
                    _mealDetail.value = Resource.Error(result.message.toString())
                }

                is Resource.Loading -> {
                    _mealDetail.value = Resource.Loading()
                }
            }
        }

    }


    fun getMealById(mealID: String) = viewModelScope.launch {
        getMealInfoRepo.getMealInfo(mealID).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val meal = result.data?.meals?.get(0)
                    _mealDetailById.value = Resource.Success(meal!!)
                }

                is Resource.Error -> {
                    _mealDetailById.value = Resource.Error(result.message.toString())
                }

                is Resource.Loading -> {
                    _mealDetailById.value = Resource.Loading()
                }
            }
        }

    }

    fun upsertMeal(meal: Meal) = viewModelScope.launch {
        localRepository.upsert(meal)
    }

}