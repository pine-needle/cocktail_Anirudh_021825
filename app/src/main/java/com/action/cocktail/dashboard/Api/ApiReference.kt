package com.action.cocktail.dashboard.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiReference {


    private const val BASE_URL = " https://www.thecocktaildb.com/api/"
    const val END_POINT = "json/v1/1/search.php"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiReference: ApiDetails = retrofit.create(ApiDetails::class.java)
}