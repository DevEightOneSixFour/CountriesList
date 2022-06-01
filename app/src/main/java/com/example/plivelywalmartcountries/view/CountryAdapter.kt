package com.example.plivelywalmartcountries.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.plivelywalmartcountries.databinding.CountryListItemBinding
import com.example.plivelywalmartcountries.databinding.HeaderListItemBinding
import com.example.plivelywalmartcountries.model.CountryResponse

sealed class ItemView {
    class HeaderView(val letter: Char) : ItemView()
    class CountryView(val country: CountryResponse) : ItemView()
}
// listOfCountries -> countries
// itemViews -> (newLetter) headerView, countryView, countryView, (newLetter) headerView, countryView
class CountryAdapter(private val list: List<ItemView>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            COUNTRY -> {
                CountryViewHolder(
                    CountryListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                HeaderViewHolder(
                    HeaderListItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.onBind((list[position] as ItemView.HeaderView).letter)
            is CountryViewHolder -> holder.onBind((list[position] as ItemView.CountryView).country)
        }
    }

    override fun getItemCount() = list.size

    // checks for the view type
    override fun getItemViewType(position: Int): Int {

       return when (list[position]) {
            is ItemView.HeaderView -> HEADER
            else -> COUNTRY
        }
    }


    companion object {
        private const val HEADER = 1
        private const val COUNTRY = 0
    }
}

class HeaderViewHolder(private val binding: HeaderListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(letter: Char) {
        binding.tvHeaderLetter.text = letter.toString()
    }
}

class CountryViewHolder(private val binding: CountryListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(country: CountryResponse) {
        binding.run {
            tvName.text = country.name
            tvRegion.text = country.region
            tvCode.text = country.code
            tvCapital.text = country.capital
        }
    }
}