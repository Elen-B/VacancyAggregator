package ru.practicum.android.diploma.filter.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Area

class LocationViewViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.filter_location_view, parentView, false)
) {

    private val countryNameView: TextView by lazy { itemView.findViewById(R.id.tvLocation) }

    fun bind(model: Area) {
        countryNameView.text = model.name
    }
}