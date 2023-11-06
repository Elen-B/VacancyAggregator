package ru.practicum.android.diploma.favourites.presentation.adapter

import android.view.TextureView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class FavouritesVacancyHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name: TextView = itemView.findViewById(R.id.text_header)
    private val employer: TextView = itemView.findViewById(R.id.text_description)
    private val salary: TextView = itemView.findViewById(R.id.text_salary)
    private val logo: ImageView = itemView.findViewById(R.id.image_placeholder)

    fun bind(model: SearchVacancy) {
        Glide
            .with(itemView)
            .load(model.logo)
            .placeholder(R.drawable.vacancy_item_search_placeholder)
            .into(logo)

        name.text = model.name
        employer.text = model.employer?.name
        salary.text = model.salary?.getSalaryToTextView()
    }
}