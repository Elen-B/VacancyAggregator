package ru.practicum.android.diploma.util

//константы уровня приложения

const val CLICK_DEBOUNCE_DELAY = 1000L
const val SEARCH_DEBOUNCE_DELAY = 2000L
const val LOG_IMAGE = "90"
const val NETWORK_ERROR = "Нет интернета"
const val VACANCY_ERROR = "Не удалось получить список вакансий"
const val UNKNOWN_ERROR = "Неизвестная ошибка"
const val SERVER_ERROR = "Ошибка сервера"
const val SALARY_NOT_SPECIFIED = "Зарплата не указана"
const val VACANCY_ID = "id"
const val PER_PAGE = "per_page"
const val TWENTY = "20"
const val TEXT = "text"
const val FOUND = "found"
const val AREA = "area"
const val SALARY = "salary"
const val INDUSTRY = "industry"
const val ONLY_WITH_SALARY = "only_with_salary"
const val FROM = "от"
const val TO = "до"
const val SPACE = " "
const val COMMA = ","
const val ZERO = 0
const val PAGE = "page"
const val ONE = 1
const val CHECK_CONNECTION = "Проверьте подключение к интернету"
const val ERROR_HAS_OCCURRED = "Произошла ошибка"
const val NOT_EXIST = "Таких вакансий нет"
const val FOUND_0_VACANCIES = "Найдено %1\$s вакансий"
const val FOUND_1_VACANCIES = "Найдена %1\$s вакансия"
const val FOUND_2_VACANCIES = "Найдено %1\$s вакансии"

const val BASE_URL = "https://api.hh.ru/"

const val SHARED_PREFS = "app_preferences"

fun getStringCount(count: String): String {
    val digit = count.last().toString().toInt()
    return if (count.takeLast(2).toInt() in 10..20) {
        FOUND_0_VACANCIES.format(count)
    } else if (digit == 1) {
        FOUND_1_VACANCIES.format(count)
    } else if (digit in 2..4) {
        FOUND_2_VACANCIES.format(count)
    } else {
        FOUND_0_VACANCIES.format(count)
    }
}