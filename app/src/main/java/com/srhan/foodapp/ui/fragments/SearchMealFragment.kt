package com.srhan.foodapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.srhan.foodapp.adapters.MealsAdapter
import com.srhan.foodapp.databinding.FragmentSearchMealBinding
import com.srhan.foodapp.util.Constants.Companion.SEARCH_MEALS_TIME_DELAY
import com.srhan.foodapp.util.Resource
import com.srhan.foodapp.viewmodel.SearchMealsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchMealFragment : Fragment() {
    private val viewModel: SearchMealsViewModel by viewModels()
    private lateinit var binding: FragmentSearchMealBinding
    private lateinit var searchAdapter: MealsAdapter
    private var searchJob: Job? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchMealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideProgressBar()
        setUpSearchRecyclerView()

        binding.etSearchMeal.addTextChangedListener { searchQuery ->
            searchJob?.cancel()
            if (searchQuery.toString().isNotEmpty()) {
                searchJob = MainScope().launch {
                    delay(SEARCH_MEALS_TIME_DELAY)
                    viewModel.searchMeal(searchQuery.toString())

                }

            } else {
                searchAdapter.differ.submitList(emptyList())

            }


        }


        binding.ivSearchIcon.setOnClickListener {
            val textSearch = binding.etSearchMeal.text.toString()
            viewModel.searchMeal(textSearch)
        }

        observeOnSearchMeal()
    }

    private fun observeOnSearchMeal() {
        lifecycleScope.launch {
            viewModel.searchMeal.collect { result ->
                showProgressBar()
                when (result) {
                    is Resource.Success -> {
                        hideProgressBar()
                        result.data?.let { mealResult ->
                            searchAdapter.differ.submitList(mealResult)
                        }
                    }

                    is Resource.Error -> {
                        hideProgressBar()
                        Log.d("SearchFragmentError", "getRandomMeal: ${result.message.toString()}")
                    }

                    is Resource.Loading -> {
                        showProgressBar()
                    }


                    else -> {}
                }

            }
        }

    }

    private fun setUpSearchRecyclerView() {
        searchAdapter = MealsAdapter()
        binding.rvSearch.apply {
            adapter = searchAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }


}