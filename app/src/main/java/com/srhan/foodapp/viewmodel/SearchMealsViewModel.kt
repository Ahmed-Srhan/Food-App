package com.srhan.foodapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srhan.foodapp.models.Meal
import com.srhan.foodapp.repository.SearchMealRepo
import com.srhan.foodapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMealsViewModel @Inject constructor(private val searchMealRepo: SearchMealRepo) :
    ViewModel() {

    private val _searchMeal: MutableStateFlow<Resource<List<Meal>>?> = MutableStateFlow(null)
    val searchMeal: StateFlow<Resource<List<Meal>>?> = _searchMeal


    fun searchMeal(searchQuery: String) = viewModelScope.launch {
        searchMealRepo.searchMeal(searchQuery).collect { result ->
            when (result) {
                is Resource.Success -> {
                    val meal = result.data?.meals
                    _searchMeal.value = Resource.Success(meal ?: emptyList())
                }

                is Resource.Error -> {
                    _searchMeal.value = Resource.Error(result.message.toString())
                }

                is Resource.Loading -> {
                    _searchMeal.value = Resource.Loading()
                }

            }
        }

    }
}