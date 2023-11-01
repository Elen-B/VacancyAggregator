package ru.practicum.android.diploma.search.domain.models

data class Salary(
    val from: String,
    val to: String,
    val currency: CurrencyType,
    val gross: Boolean = false
)

enum class CurrencyType{
    RUR, USD, EUR
}