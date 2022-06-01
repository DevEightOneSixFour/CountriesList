package com.example.plivelywalmartcountries.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.plivelywalmartcountries.databinding.ActivityMainBinding
import com.example.plivelywalmartcountries.di.DI
import com.example.plivelywalmartcountries.model.CountryResponse
import com.example.plivelywalmartcountries.model.UIState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by lazy {
        DI.provideViewModel(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureObserver()
    }

    private fun configureObserver() {
        viewModel.countryLiveData.observe(this) {
            when(it) {
                is UIState.Loading -> {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        tvErrorText.visibility = View.GONE
                        rvCountries.visibility = View.GONE
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        tvErrorText.text = it.msg
                        tvErrorText.visibility = View.VISIBLE
                        rvCountries.visibility = View.GONE
                    }
                }
                is UIState.Success -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        tvErrorText.visibility = View.GONE

                        rvCountries.apply {
                            adapter = CountryAdapter(countriesWithHeaders(it.countryResponses))
                            layoutManager = LinearLayoutManager(this@MainActivity)
                            visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun countriesWithHeaders(countries: List<CountryResponse>): List<ItemView> {
        // list to return
        val itemViewList = mutableListOf<ItemView>()
        // a character that cannot exist in our list
        var currentLetter = '1' //B

        // sort the list of countries by the name
        countries.sortedBy { it.name }.forEach { countryResponse ->
            // get the first char of the name
            countryResponse.name.first().apply {
                // if this char is not the current letter we are using
                // add that letter to our header
                // else it is our current letter and add the country
                if (this != currentLetter) {
                    itemViewList.add(ItemView.HeaderView(this))
                    currentLetter = this // B
                }
                itemViewList.add(ItemView.CountryView(countryResponse))
            }
        }
        return itemViewList
    }
}
