package com.example.plivelywalmartcountries.network

import com.example.plivelywalmartcountries.model.CountryResponse
import retrofit2.Response

interface CountryRepository {
    suspend fun getCountries(): Response<List<CountryResponse>>
}

class CountryRepositoryImpl(
    private val countryService: CountryService
): CountryRepository {
    override suspend fun getCountries(): Response<List<CountryResponse>> =
        countryService.getCountries()
}