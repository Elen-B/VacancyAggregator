package ru.practicum.android.diploma.favourites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

class FavouritesVacancyHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item, parent, false)
) {

    private val name: TextView = itemView.findViewById(R.id.text_header)
    private val employer: TextView = itemView.findViewById(R.id.text_description)
    private val salary: TextView = itemView.findViewById(R.id.text_salary)
    private val logo: ImageView = itemView.findViewById(R.id.image_placeholder)

    fun bind(model: ProfessionDetail) {
        Glide
            .with(itemView)
            .load(model.employer?.logo)
            .placeholder(R.drawable.ic_logo)
            .into(logo)

        name.text = model.name
        employer.text = model.employer?.name
        salary.text = model.salary?.getSalaryToText()
    }
}