package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.presentation.models.FilterScreenState
import ru.practicum.android.diploma.util.SingleEventLiveData

class FilterViewModel : ViewModel() {
    private val filterParameters: FilterParameters by lazy { getSavedFilterParameters() }

    private val stateLiveData = MutableLiveData<FilterScreenState>()
    fun observeState(): LiveData<FilterScreenState> = stateLiveData

    private val showLocationTrigger = SingleEventLiveData<FilterParameters>()
    fun getShowLocationTrigger(): LiveData<FilterParameters> = showLocationTrigger

    init {
        setState(FilterScreenState.Started(filterParameters))
    }

    private fun getSavedFilterParameters(): FilterParameters {
        //здесь получение данных из SharedPreferences
        return FilterParameters(salary = 40000, fSalaryRequired = true)
    }

    private fun setState(state: FilterScreenState) {
        stateLiveData.value = state
    }

    private fun getCurrentState(newFilterParameters: FilterParameters): FilterScreenState {
        return if (filterParameters.equals(newFilterParameters) && stateLiveData.value is FilterScreenState.Initial) {
            FilterScreenState.Initial
        } else {
            FilterScreenState.Modified(newFilterParameters, false)
        }
    }

    private fun setFilterParameters(newFilterParameters: FilterParameters) {
        filterParameters.country = newFilterParameters.country
        filterParameters.region = newFilterParameters.region
        filterParameters.industry = newFilterParameters.industry
        filterParameters.salary = newFilterParameters.salary
        filterParameters.fSalaryRequired = newFilterParameters.fSalaryRequired
    }

    fun onSalaryChanged(value: String) {
        val salary = if (value.isNotEmpty()) value.toInt() else null
        val newFilterParameters = filterParameters.copy(salary = salary)
        setState(getCurrentState(newFilterParameters))
        setFilterParameters(newFilterParameters)
    }

    fun showLocation() {
        // добавить clickDebounce
        showLocationTrigger.value = filterParameters
    }
}