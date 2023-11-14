package ru.practicum.android.diploma.filter.presentation.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.domain.models.Area

class LocationViewAdapter(private var items: List<Area>) : RecyclerView.Adapter<LocationViewViewHolder>() {
    var clickListener: CountryClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewViewHolder {
        return LocationViewViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: LocationViewViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{clickListener?.onCountryClick(item)}
    }

    fun addItems(values: List<Area>) {
        items = values
        this.notifyDataSetChanged()
    }

    fun interface CountryClickListener {
        fun onCountryClick(area: Area)
    }
}