package ru.practicum.android.diploma.core.domain.models

import ru.practicum.android.diploma.util.COMMA
import ru.practicum.android.diploma.util.FROM
import ru.practicum.android.diploma.util.SALARY_NOT_SPECIFIED
import ru.practicum.android.diploma.util.SPACE
import ru.practicum.android.diploma.util.TO
import java.text.NumberFormat
import java.util.Locale

data class Salary(
    val from: Int?,
    val to: Int?,
    val currency: Currency,
    val gross: Boolean = false
){
    fun getSalaryToText(): String{
        val text = StringBuilder()
        if(from == null && to == null){
            text.append(SALARY_NOT_SPECIFIED)
        }
        else if(from == null){
            text.append("$FROM ${spacedNumber(to)} ${currency.symbol}")
        }
        else if(to == null){
            text.append("$FROM ${spacedNumber(from)} ${currency.symbol}")
        }
        else{
            text.append("$FROM ${spacedNumber(from)} $TO ${spacedNumber(to)} ${currency.symbol}")
        }
        return text.toString()
    }

    private fun spacedNumber(value: Int?): String {
        val numberFormat = NumberFormat.getNumberInstance(Locale.US)
        return numberFormat
            .format(value)
            .replace(COMMA,SPACE)
    }
}
