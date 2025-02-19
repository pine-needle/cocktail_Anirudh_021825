package com.action.cocktail.dashboard.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.action.cocktail.dashboard.Model.CocktailModel
import com.action.cocktail.dashboard.Model.DrinkModel
import com.action.cocktail.dashboard.vm.DashboardViewModel
import com.action.cocktail.dashboard.vm.CockTailAdapter
import com.action.cocktail.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        // Fetch cocktail data on fragment creation
        dashboardViewModel.getCockTailByFirstLetter()

        // Observe API response
        dashboardViewModel.cockTail.observe(viewLifecycleOwner) { cocktailModel ->
            if (!cocktailModel.drinks.isNullOrEmpty()) {
                setupUI(cocktailModel)
            } else {
                Log.d("RecyclerView", "No cocktails found, not updating UI")
            }
        }

        return binding.root
    }

    private fun setupUI(cocktailModel: CocktailModel) {
        binding.rvcocktail.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CockTailAdapter(cocktailModel.drinks ?: emptyList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

