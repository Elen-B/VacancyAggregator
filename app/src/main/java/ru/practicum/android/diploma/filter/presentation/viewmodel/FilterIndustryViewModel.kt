package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.presentation.models.FilterIndustryScreenState

class FilterIndustryViewModel: ViewModel() {

    private val stateLiveData = MutableLiveData<FilterIndustryScreenState>()
    fun observeState(): LiveData<FilterIndustryScreenState> = stateLiveData


    init {
        loadData()
    }

    private fun loadData() {
        setState(
            FilterIndustryScreenState.Content(
                listOf(
                    Industry("111", "IT"),
                    Industry("112", "Право")
                ), Industry("111", "IT")
            )
        )
    }

    private fun setState(state: FilterIndustryScreenState) {
        stateLiveData.value = state
    }
}