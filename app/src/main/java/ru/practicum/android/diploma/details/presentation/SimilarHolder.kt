package ru.practicum.android.diploma.details.presentation

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ItemSimilarBinding
import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar
import ru.practicum.android.diploma.util.formattedNumber
import ru.practicum.android.diploma.util.setSymbolByCurrency

class SimilarHolder (private val binding: ItemSimilarBinding, private val context: Context):
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun bind (item: ProfessionSimillar) {
        Glide
            .with(this.itemView.context)
            .load(item.employerLogo)
            .placeholder(R.drawable.vacancy_item_search_placeholder)
            .into(binding.similarLogo)
        binding.nameSimilar.text = if (item.city!=null) {
            "${item.name}, ${item.city}"
        } else {
            item.name
        }
        binding.nameSimilarEmployer.text = item.employerName
        val from = context.getString(R.string.from)
        val to = context.getString(R.string.to)
        val notSalary = context.getString(R.string.not_salary)
        if (item.salaryFrom == null && item.salaryTo == null) {
            binding.salarySimilar.text = notSalary
        } else if (item.salaryFrom != null && item.salaryTo == null) {
            binding.salarySimilar.text =
                "$from ${item.salaryFrom.formattedNumber()} ${setSymbolByCurrency(item.salaryCurrency)}"
        } else if (item.salaryFrom == null && item.salaryTo != null) {
            binding.salarySimilar.text =
                "$to ${item.salaryTo.formattedNumber()} ${setSymbolByCurrency(item.salaryCurrency)}"
        } else if (item.salaryFrom != null && item.salaryTo != null) {
            binding.salarySimilar.text =
                "$from ${item.salaryFrom.formattedNumber()} $to ${item.salaryTo.formattedNumber()} ${
                    setSymbolByCurrency(
                        item.salaryCurrency
                    )
                }"
        }
    }
}