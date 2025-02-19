package com.action.cocktail.home.Api


import com.action.cocktail.home.Model.CocktailModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDetails {

    @GET(ApiReference.END_POINT)
    suspend fun getRandomCocktail(): CocktailModel
}