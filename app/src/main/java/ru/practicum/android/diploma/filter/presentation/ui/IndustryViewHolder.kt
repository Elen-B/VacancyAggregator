package ru.practicum.android.diploma.filter.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.filter.domain.models.Industry

class IndustryViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.filter_industry_view, parentView, false)
) {

    private val industryNameView: TextView by lazy { itemView.findViewById(R.id.rbIndustry) }

    fun bind(model: Industry) {
        industryNameView.text = model.name
    }
}