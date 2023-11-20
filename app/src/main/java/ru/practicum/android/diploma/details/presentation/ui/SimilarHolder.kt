package ru.practicum.android.diploma.details.presentation.ui

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.databinding.ItemSimilarBinding

class SimilarHolder (private val binding: ItemSimilarBinding):
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind (item: Vacancy) {
        Glide
            .with(this.itemView.context)
            .load(item.employer?.logo)
            .placeholder(R.drawable.ic_logo)
            .into(binding.similarLogo)
        binding.nameSimilar.text = if (item.area!=null) {
            "${item.name}, ${item.area.name}"
        } else {
            item.name
        }
        binding.nameSimilarEmployer.text = item.employer?.name.orEmpty()
        binding.salarySimilar.text = item.salary?.getSalaryToText().orEmpty()
    }
}