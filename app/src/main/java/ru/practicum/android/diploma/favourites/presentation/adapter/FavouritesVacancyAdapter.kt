package ru.practicum.android.diploma.favourites.presentation.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class FavouritesVacancyAdapter(private val onClickItem: (SearchVacancy) -> Unit) :
    RecyclerView.Adapter<FavouritesVacancyHolder>() {
    private val vacancyItems = mutableListOf<SearchVacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesVacancyHolder =
        FavouritesVacancyHolder(parent)

    override fun onBindViewHolder(holder: FavouritesVacancyHolder, position: Int) {
        holder.bind(vacancyItems[position])
        holder.itemView.setOnClickListener {
            onClickItem.invoke(vacancyItems[position])
        }
    }

    override fun getItemCount(): Int {
        return vacancyItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setVacancyList(newVacancyList: List<SearchVacancy>?) {
        vacancyItems.clear()
        if (!newVacancyList.isNullOrEmpty()) {
            vacancyItems.addAll(newVacancyList)
        }
        notifyDataSetChanged()
    }
}