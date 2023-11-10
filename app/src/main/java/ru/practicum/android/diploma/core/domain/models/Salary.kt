package ru.practicum.android.diploma.core.domain.models

import java.text.NumberFormat
import java.util.Locale

data class Salary(
    val from: Int?,
    val to: Int?,
    val currency: Currency,
    val gross: Boolean = false
){
    fun getSalaryToTextView(): String{
        val text = StringBuilder()
        if(from == null && to == null){
            text.append("Зарплата не указана")
        }
        else if(from == null){
            text.append("до ${spacedNumber(to)} ${currency.symbol}")
        }
        else if(to == null){
            text.append("от ${spacedNumber(from)} ${currency.symbol}")
        }
        else{
            text.append("от ${spacedNumber(from)} до ${spacedNumber(to)} ${currency.symbol}")
        }
        return text.toString()
    }

    private fun spacedNumber(value: Int?): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        return numberFormat
            .format(value)
            .replace(","," ")
    }
}
