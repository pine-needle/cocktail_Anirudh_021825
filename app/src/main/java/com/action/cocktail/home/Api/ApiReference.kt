package com.action.cocktail.home.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiReference {


    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"
    const val END_POINT = "random.php"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiReference: ApiDetails = retrofit.create(ApiDetails::class.java)
}