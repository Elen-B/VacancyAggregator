package ru.practicum.android.diploma.search.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

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


    fun bind(vacancy: SearchVacancy) {
        Glide.with(itemView)
            .load(vacancy.logo)
            .placeholder(R.drawable.ic_logo)
            .into(logView)
        nameView.text = vacancy.name
        employerView.text = vacancy.employer?.name
        salaryView.text = vacancy.salary?.getSalaryToTextView() ?: "Зарплата не указана"
        itemView.setOnClickListener { clickListener.onVacancyClick(vacancy) }
    }
}

interface ItemClickListener {
    fun onVacancyClick(vacancy: SearchVacancy)
}