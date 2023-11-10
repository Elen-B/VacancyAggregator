package ru.practicum.android.diploma.details.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.databinding.ItemSimilarBinding

class SimilarAdapter(
    private val similarVacancies: List<Vacancy>,
    private val onClickListener: OnStateClickListener
) : RecyclerView.Adapter<SimilarHolder>() {
    interface OnStateClickListener {
        fun onStateClick(item: Vacancy, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SimilarHolder(ItemSimilarBinding.inflate(inflater, parent, false))
    }

    override fun getItemCount(): Int {
        return similarVacancies.size
    }

    override fun onBindViewHolder(holder: SimilarHolder, position: Int) {
        val item: Vacancy = similarVacancies[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onStateClick(item, position)
        }
    }
}