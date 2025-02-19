package com.action.cocktail.dashboard.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.action.cocktail.dashboard.Api.ApiReference
import com.action.cocktail.dashboard.Model.CocktailModel
import com.action.cocktail.dashboard.Model.DrinkModel
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    private val _cockTail: MutableLiveData<CocktailModel> = MutableLiveData()
    val cockTail: LiveData<CocktailModel> = _cockTail

    fun getCockTailByFirstLetter() {
        viewModelScope.launch {
            try {
                val result = ApiReference.apiReference.getCockTailByFirstLetter(firstLetter = "c")
                if (result.drinks.isNullOrEmpty()) {
                    Log.d("API Error", "getCockTailByFirstLetter: Cocktail data is empty")
                } else {
                    _cockTail.postValue(result)
                }
            } catch (e: Exception) {
                Log.e("API Error", "Failed to fetch data: ${e.message}")
            }
        }
    }
}


    
