package com.action.cocktail.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.action.cocktail.databinding.FragmentHomeBinding
import com.action.cocktail.home.vm.HomeViewModel
import com.bumptech.glide.Glide

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val viewModel: HomeViewModel by viewModels()

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!

    @OptIn(UnstableApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.randomCocktail.observe(viewLifecycleOwner) { drink ->
            Log.d("Home Fragment", "$drink")
            if (drink != null) {
                binding.cocktailName.text = drink.strDrink
                binding.cocktailCategory.text = "Category: ${drink.strCategory}"
                binding.cocktailGlass.text = "Glass: ${drink.strGlass}"
                binding.cocktailInstructions.text = drink.strInstructions

                // Set ingredients dynamically
                val ingredientsContainer = binding.ingredientsContainer
                ingredientsContainer.removeAllViews()
                val ingredients = listOf(
                    drink.strIngredient1, drink.strIngredient2, drink.strIngredient3
                ).filter { it != null }

                ingredients.forEach { ingredient ->
                    val ingredientTextView = TextView(requireContext()).apply {
                        text = ingredient
                        textSize = 16f
                        //setTextColor(Color.b)
                        setPadding(0, 4, 0, 4)
                    }
                    ingredientsContainer.addView(ingredientTextView)
                }

                Glide.with(this)
                    .load(drink.strDrinkThumb)
                    .into(binding.cocktailImage)

            } else {
                binding.cocktailName.text = "Failed to load data"
            }
        }

        binding.randomBtn.setOnClickListener {
            viewModel.getRandomCocktail()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


