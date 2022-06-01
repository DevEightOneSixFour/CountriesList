package com.example.plivelywalmartcountries.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.plivelywalmartcountries.network.CountryRepositoryImpl
import com.example.plivelywalmartcountries.network.CountryService
import com.example.plivelywalmartcountries.utils.BASE_URL
import com.example.plivelywalmartcountries.viewmodel.CountryViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DI {
    private val countryService : CountryService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryService::class.java)
    }

    private fun provideRepository() = CountryRepositoryImpl(countryService)

    fun provideViewModel(storeOwner: ViewModelStoreOwner): CountryViewModel =
        buildViewModel(storeOwner)[CountryViewModel::class.java]

    private fun buildViewModel(viewModelStoreOwner: ViewModelStoreOwner) =
        ViewModelProvider(viewModelStoreOwner,
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>) =
                    CountryViewModel(provideRepository()) as T
            })
}