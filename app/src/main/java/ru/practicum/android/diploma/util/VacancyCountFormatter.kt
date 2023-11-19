package ru.practicum.android.diploma.util

import android.content.Context
import ru.practicum.android.diploma.R

class VacancyCountFormatter(private val context: Context) {

    fun getStringCount(count: String): String {
        val digit = count.last().toString().toInt()
        if (count.takeLast(2).toInt() in 10..20) {
            return context.getString(R.string.found0Vacancies, count)
        } else if (digit == 1) {
            return context.getString(R.string.found1Vacancies, count)
        } else if (digit in 2..4) {
            return context.getString(R.string.found2Vacancies, count)
        } else {
            return context.getString(R.string.found0Vacancies, count)
        }
    }
}

//0,5-20, 25-30, 35-40, 45-50  вакансий
//1, 21, 31, 41 вакансия
//2-4, 22-24, 32-34, 42-44 вакансии