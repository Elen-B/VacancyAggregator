package ru.practicum.android.diploma.filter.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Industry

class IndustryViewHolder(parentView: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parentView.context)
        .inflate(R.layout.filter_industry_view, parentView, false)
) {

    private val industryNameView: RadioButton by lazy { itemView.findViewById(R.id.rbIndustry) }

    fun bind(model: Industry, checkedIndustry: Industry?) {
        industryNameView.text = model.name
        industryNameView.isChecked = model == checkedIndustry
    }
}