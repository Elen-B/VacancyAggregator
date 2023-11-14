package ru.practicum.android.diploma.search.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class SearchVacancyAdapter(private val clickListener: ItemClickListener
    ) : RecyclerView.Adapter<SearchVacancyViewHolder> () {

    var searchVacancyList = ArrayList<SearchVacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVacancyViewHolder {
        return SearchVacancyViewHolder(parent,clickListener)
    }
    override fun onBindViewHolder(holder: SearchVacancyViewHolder, position: Int) {
        holder.bind(searchVacancyList.get(position))
    }
    override fun getItemCount() = searchVacancyList.size

}