package ru.practicum.android.diploma.search.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.core.domain.models.Vacancy

class SearchVacancyAdapter(
    private val clickListener: ItemClickListener,
    private val onEndOfListListener: OnEndOfListListener

) : RecyclerView.Adapter<SearchVacancyViewHolder>() {

    var searchVacancyList = ArrayList<Vacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVacancyViewHolder {
        return SearchVacancyViewHolder(parent, clickListener, onEndOfListListener)
    }

    override fun onBindViewHolder(holder: SearchVacancyViewHolder, position: Int) {
        holder.bind(searchVacancyList.get(position))
        if (position == getItemCount() - 1) {
            onEndOfListListener.onEndOfList();
        }
    }
    override fun getItemCount() = searchVacancyList.size
}