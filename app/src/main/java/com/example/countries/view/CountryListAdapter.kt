package com.example.countries.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.countries.R
import com.example.countries.model.Country
import com.example.countries.util.getProgressDrawable
import com.example.countries.util.loadImage


class CountryListAdapter(var countries: ArrayList<Country>) :
    RecyclerView.Adapter<CountryListAdapter.CountryViewholder>() {

    fun updateCountries(newCountries: List<Country>){
        countries.clear()
        countries.addAll(newCountries)
        notifyDataSetChanged()
    }

    class CountryViewholder(view: View) : RecyclerView.ViewHolder(view) {
        private val countryName = view.findViewById<TextView>(R.id.country_name)
        private val countryCapital = view.findViewById<TextView>(R.id.country_capital)
        private val flagImage = view.findViewById<ImageView>(R.id.image_view)
        private val progressDrawable = getProgressDrawable(view.context)

        fun bind(country: Country) {
            countryName.text = country.name
            countryCapital.text = country.capital
            flagImage.loadImage(country.flag,progressDrawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CountryViewholder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
    )


    override fun onBindViewHolder(holder: CountryViewholder, position: Int) {
        holder.bind(countries[position])
    }

    override fun getItemCount() = countries.size
}