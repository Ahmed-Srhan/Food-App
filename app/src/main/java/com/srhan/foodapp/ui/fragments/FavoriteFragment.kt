package com.srhan.foodapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.srhan.foodapp.adapters.MealsAdapter
import com.srhan.foodapp.databinding.FragmentFavoriteBinding
import com.srhan.foodapp.viewmodel.LocalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    lateinit var favAdapter: MealsAdapter
    private val viewModel: LocalViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpFavoriteRecyclerView()
        viewModel.getAllFavoriteMeals()
        handleFavoriteMealsChange()


        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val meal = favAdapter.differ.currentList[position]
                viewModel.deleteMeal(meal)
                Snackbar.make(view, "Meal deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("undo") {
                        viewModel.upsertMeal(meal)
                    }
                    show()
                }
            }

        }


        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
    }

    private fun handleFavoriteMealsChange() {
        lifecycleScope.launch {
            viewModel.meals.collect { meals ->
                favAdapter.differ.submitList(meals)

            }
        }
    }

    private fun setUpFavoriteRecyclerView() {
        favAdapter = MealsAdapter()
        binding.favRecView.apply {
            adapter = favAdapter
            layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        }
    }


}