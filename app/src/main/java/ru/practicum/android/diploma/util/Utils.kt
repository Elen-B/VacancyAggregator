package ru.practicum.android.diploma.util

import java.text.NumberFormat
import java.util.Locale

//константы уровня приложения

const val CLICK_DEBOUNCE_DELAY = 1000L
const val SEARCH_DEBOUNCE_DELAY = 2000L
const val LOG_IMAGE = "90"
const val NETWORK_ERROR = "Нет интернета"
const val VACANCY_ERROR = "Не удалось получить список вакансий"
const val UNKNOWN_ERROR = "Неизвестная ошибка"
const val VACANCY_ID = "id"

const val BASE_URL = "https://api.hh.ru/"

const val SHARED_PREFS = "app_preferences"

const val RUR = "RUR"
const val RUB = "RUB"
const val BYR = "BYR"
const val USD = "USD"
const val EUR = "EUR"
const val KZT = "KZT"
const val UAH = "UAH"
const val AZN = "AZN"
const val UZS = "UZS"
const val GEL = "GEL"
const val KGT = "KGT"

const val SYMBOL_RUB = "₽"
const val SYMBOL_BYR = "Br"
const val SYMBOL_USD = "$"
const val SYMBOL_EUR = "€"
const val SYMBOL_KZT = "₸"
const val SYMBOL_UAH = "₴"
const val SYMBOL_AZN = "₼"
const val SYMBOL_UZS = "som"
const val SYMBOL_GEL = "₾"
const val SYMBOL_KGT = "с"

fun Int.formattedNumber(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale.US)
    return numberFormat
        .format(this)
        .replace(",", " ")
}

fun setSymbolByCurrency(currency: String?): String {
    return when (currency) {
        RUR, RUB -> SYMBOL_RUB
        BYR -> SYMBOL_BYR
        USD -> SYMBOL_USD
        EUR -> SYMBOL_EUR
        KZT -> SYMBOL_KZT
        UAH -> SYMBOL_UAH
        AZN -> SYMBOL_AZN
        UZS -> SYMBOL_UZS
        GEL -> SYMBOL_GEL
        KGT -> SYMBOL_KGT
        else -> SYMBOL_RUB
    }
}