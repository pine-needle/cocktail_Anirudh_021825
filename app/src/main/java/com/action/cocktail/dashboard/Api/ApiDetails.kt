package com.action.cocktail.dashboard.Api

import com.action.cocktail.dashboard.Model.CocktailModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDetails {

    @GET(ApiReference.END_POINT)
    suspend fun getCockTailByFirstLetter(
        @Query("f") firstLetter: String="c"
    ): CocktailModel
}