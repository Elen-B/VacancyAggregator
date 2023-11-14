package ru.practicum.android.diploma.search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.util.SALARY_NOT_SPECIFIED

class SearchVacancyViewHolder(
    parent: ViewGroup,
    private val clickListener: ItemClickListener,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item, parent, false)
) {


    var logView: ImageView = itemView.findViewById(R.id.image_placeholder)
    var nameView: TextView = itemView.findViewById(R.id.text_header)
    var employerView: TextView = itemView.findViewById(R.id.text_description)
    var salaryView: TextView = itemView.findViewById(R.id.text_salary)


    fun bind(vacancy: Vacancy) {
        Glide.with(itemView)
            .load(vacancy.employer?.logo)
            .placeholder(R.drawable.ic_logo)
            .into(logView)
        nameView.text = "${vacancy.name}, ${vacancy.area?.name}"
        employerView.text = vacancy.employer?.name
        salaryView.text = vacancy.salary?.getSalaryToTextView() ?: SALARY_NOT_SPECIFIED
        itemView.setOnClickListener { clickListener.onVacancyClick(vacancy) }
    }
}

interface ItemClickListener {
    fun onVacancyClick(vacancy: Vacancy)
}