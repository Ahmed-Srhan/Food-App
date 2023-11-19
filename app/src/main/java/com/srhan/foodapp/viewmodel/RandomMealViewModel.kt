package com.srhan.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.repository.GetRandomMealRepo
import com.srhan.foodapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RandomMealViewModel @Inject constructor(private val getRandomMealRepo: GetRandomMealRepo) :
    ViewModel() {


    private val _randomMeals: MutableStateFlow<Resource<Meal>?> = MutableStateFlow(null)
    val randomMeals: StateFlow<Resource<Meal>?> get() = _randomMeals


    fun getRandomMeals() = viewModelScope.launch {
        getRandomMealRepo.getRandomMeal().collect { result ->
            when (result) {
                is Resource.Success -> {
                    val meal = result.data?.meals?.get(0)
                    _randomMeals.value = Resource.Success(meal!!)
                }

                is Resource.Error -> {
                    _randomMeals.value = Resource.Error(result.message.toString())
                }

                is Resource.Loading -> {
                    _randomMeals.value = Resource.Loading()
                }

            }
        }

    }

}