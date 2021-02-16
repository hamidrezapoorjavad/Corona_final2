package com.example.corona_final.fragments.country

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.corona_final.data.Country
import com.example.corona_final.databinding.ItemCountryBinding

class CountryAdapter(private val context: Context , val itemClick : (country :Country) -> Unit) :
    ListAdapter<Country, CountryAdapter.CountryViewHolder>(CountryDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemCountryBinding.inflate(inflater, parent, false)
        return CountryViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val item = currentList[position]
        holder.binding.data = item
//        holder.binding.tvCountry.text = item.country
//        holder.binding.tvIdeath.text = item.totalDeaths.toString()
//        holder.binding.tvNewDeath.text = item.newDeaths.toString()
        holder.itemView.setOnClickListener { itemClick.invoke(item)}



    }


    class CountryViewHolder(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    class CountryDiffUtil : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.iD == newItem.iD
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }

    }
}
