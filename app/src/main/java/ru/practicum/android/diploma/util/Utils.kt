package ru.practicum.android.diploma.util

import java.text.NumberFormat
import java.util.Locale

//константы уровня приложения

const val CLICK_DEBOUNCE_DELAY = 1000L
const val SEARCH_DEBOUNCE_DELAY = 2000L


const val BASE_URL = "https://api.hh.ru/"


fun Int.formattedNumber(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.US)
    return numberFormat
        .format(this)
        .replace(",", " ")
}