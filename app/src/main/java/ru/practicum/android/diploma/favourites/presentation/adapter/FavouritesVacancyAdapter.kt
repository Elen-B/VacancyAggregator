package ru.practicum.android.diploma.favourites.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class FavouritesVacancyAdapter(private val onClickItem: (SearchVacancy) -> Unit) :
    RecyclerView.Adapter<FavouritesVacancyHolder>() {
    private val vacancyItems = mutableListOf<SearchVacancy>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesVacancyHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vacancy_item, parent, false)
        return FavouritesVacancyHolder(view)
    }
    override fun onBindViewHolder(holder: FavouritesVacancyHolder, position: Int) {
        holder.bind(vacancyItems[position])
        holder.itemView.setOnClickListener {
            onClickItem.invoke(vacancyItems[position])
        }
    }
    override fun getItemCount(): Int {
        return vacancyItems.size
    }

    fun setVacancyList(newVacancyList: List<SearchVacancy>?){
        vacancyItems.clear()
        if (!newVacancyList.isNullOrEmpty()){
            vacancyItems.addAll(newVacancyList)
        }
        notifyDataSetChanged()
    }

}