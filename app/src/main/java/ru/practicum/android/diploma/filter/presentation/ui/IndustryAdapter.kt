package ru.practicum.android.diploma.filter.presentation.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.practicum.android.diploma.filter.domain.models.Industry

class IndustryAdapter(private var items: List<Industry>) : RecyclerView.Adapter<IndustryViewHolder>() {
    var clickListener: IndustryClickListener? = null
    var checkedIndustry: Industry? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndustryViewHolder {
        return IndustryViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: IndustryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, checkedIndustry)
        holder.itemView.setOnClickListener{clickListener?.onIndustryClick(item)}
    }

    fun addItems(values: List<Industry>, checkedIndustry: Industry?) {
        items = values
        this.checkedIndustry = checkedIndustry
        this.notifyDataSetChanged()
    }

    fun interface IndustryClickListener {
        fun onIndustryClick(industry: Industry)
    }
}