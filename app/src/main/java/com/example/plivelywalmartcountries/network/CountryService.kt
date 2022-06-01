package com.example.plivelywalmartcountries.network

import com.example.plivelywalmartcountries.model.CountryResponse
import com.example.plivelywalmartcountries.utils.ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET(ENDPOINT)
    suspend fun getCountries(): Response<List<CountryResponse>>
}