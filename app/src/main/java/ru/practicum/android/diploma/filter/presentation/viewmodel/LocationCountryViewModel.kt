package ru.practicum.android.diploma.filter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor

class LocationCountryViewModel(private val filterInteractor: FilterInteractor): ViewModel()  {
    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val result = filterInteractor.getCountries()
            if (!result.second.isNullOrEmpty()) {
                logShowMessage("ошибка: " + result.second)
            } else {
                if (result.first != null) {
                    logShowMessage("список стран: " + result.first.toString())
                } else {
                    logShowMessage("список стран пуст почему-то")
                }
            }

        }
    }

    private fun logShowMessage(message: String) {
        Log.e("filter", message)
    }
}