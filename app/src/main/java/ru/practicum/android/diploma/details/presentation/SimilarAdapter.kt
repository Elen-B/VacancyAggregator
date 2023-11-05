package ru.practicum.android.diploma.details.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.databinding.ItemSimilarBinding
import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar

class SimilarAdapter(
    private val similarsVacancies: List<ProfessionSimillar>,
    private val onClickListener: OnStateClickListener,
    private val context: Context
) : RecyclerView.Adapter<SimilarHolder>() {
    interface OnStateClickListener {
        fun onStateClick(item: ProfessionSimillar, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SimilarHolder(ItemSimilarBinding.inflate(inflater, parent, false), context)
    }

    override fun getItemCount(): Int {
        return similarsVacancies.size
    }

    override fun onBindViewHolder(holder: SimilarHolder, position: Int) {
        val item: ProfessionSimillar = similarsVacancies[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onStateClick(item, position)
        }
    }
}