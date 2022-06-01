package com.example.plivelywalmartcountries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plivelywalmartcountries.model.UIState
import com.example.plivelywalmartcountries.network.CountryRepositoryImpl
import kotlinx.coroutines.launch

class CountryViewModel(private val repository: CountryRepositoryImpl): ViewModel() {

    private val _countryLiveData =  MutableLiveData<UIState>()
    val countryLiveData: LiveData<UIState>
        get() = _countryLiveData

    init {
        // to only call get countries when the ViewModel is created
        getCountries()
    }

    private fun getCountries() {
        _countryLiveData.value = UIState.Loading
        viewModelScope.launch {
            val response = repository.getCountries()
            if (response.isSuccessful) {
                _countryLiveData.postValue(UIState.Success(response.body()!!))
            } else {
                _countryLiveData.postValue(UIState.Error("Failed network call"))
            }
        }
    }
}