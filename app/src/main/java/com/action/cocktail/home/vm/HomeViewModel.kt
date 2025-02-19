package com.action.cocktail.home.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.action.cocktail.home.Api.ApiReference
import com.action.cocktail.home.Model.DrinkModel
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _randomCocktail = MutableLiveData<DrinkModel?>()
    val randomCocktail: MutableLiveData<DrinkModel?> get() = _randomCocktail

        fun getRandomCocktail() {
            viewModelScope.launch {
                val result = ApiReference.apiReference.getRandomCocktail()
                val drink = result.drinks.firstOrNull()

                if (result!= null) {
                    _randomCocktail.postValue(drink)
                } else {
                    Log.d("API Error", "getCocktailByFirstLetter: No valid drink found")
                }

            }
        }
    }


