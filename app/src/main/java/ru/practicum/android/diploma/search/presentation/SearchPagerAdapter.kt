package ru.practicum.android.diploma.search.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class SearchPagerAdapter(
    private val clickListener: ItemClickListener
) : PagingDataAdapter<SearchVacancy, SearchVacancyViewHolder>(SearchComparator) {


    override fun onBindViewHolder(holder: SearchVacancyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVacancyViewHolder {
        return SearchVacancyViewHolder(parent, clickListener)

    }

    object SearchComparator : DiffUtil.ItemCallback<SearchVacancy>() {
        override fun areItemsTheSame(oldItem: SearchVacancy, newItem: SearchVacancy): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: SearchVacancy, newItem: SearchVacancy): Boolean {
            return oldItem == newItem
        }
    }
}