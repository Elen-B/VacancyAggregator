package ru.practicum.android.diploma.search.presentation

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.practicum.android.diploma.core.domain.models.Vacancy

class SearchPagerAdapter(
    private val clickListener: ItemClickListener
) : PagingDataAdapter<Vacancy, SearchVacancyViewHolder>(SearchComparator) {


    override fun onBindViewHolder(holder: SearchVacancyViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVacancyViewHolder {
        return SearchVacancyViewHolder(parent, clickListener)

    }

    object SearchComparator : DiffUtil.ItemCallback<Vacancy>() {
        override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean {
            return oldItem == newItem
        }
    }
}