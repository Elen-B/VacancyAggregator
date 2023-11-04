package ru.practicum.android.diploma.filter.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Area

class LocationCountryViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.filter_location_view, parentView, false)
) {

    private val trackNameView: TextView by lazy { itemView.findViewById(R.id.tvLocation) }

    fun bind(model: Area) {
        trackNameView.text = model.name
    }
}