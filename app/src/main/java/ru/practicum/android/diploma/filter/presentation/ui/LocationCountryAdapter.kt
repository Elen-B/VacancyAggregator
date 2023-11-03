package ru.practicum.android.diploma.filter.presentation.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.domain.models.Area

class LocationCountryAdapter(private var items: List<Area>) : RecyclerView.Adapter<LocationCountryViewHolder>() {
    var clickListener: CountryClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationCountryViewHolder {
        return LocationCountryViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LocationCountryViewHolder, position: Int) {
        val track = items[position]
        holder.bind(track)
        holder.itemView.setOnClickListener{clickListener?.onCountryClick(track)}
    }

    fun addItems(values: List<Area>) {
        items = values
        this.notifyDataSetChanged()
    }

    fun interface CountryClickListener {
        fun onCountryClick(area: Area)
    }
}