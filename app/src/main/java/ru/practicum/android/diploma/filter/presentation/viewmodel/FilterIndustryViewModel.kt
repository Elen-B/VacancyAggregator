package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Industry
import ru.practicum.android.diploma.filter.presentation.models.FilterIndustryScreenState
import ru.practicum.android.diploma.util.SingleEventLiveData

class FilterIndustryViewModel(industry: Industry?, private val filterInteractor: FilterInteractor): ViewModel() {

    private var checkedIndustry: Industry?

    private val stateLiveData = MutableLiveData<FilterIndustryScreenState>()
    fun observeState(): LiveData<FilterIndustryScreenState> = stateLiveData

    private val applyFilterTrigger = SingleEventLiveData<Industry?>()
    fun getApplyFilterTrigger(): LiveData<Industry?> = applyFilterTrigger

    init {
        checkedIndustry = industry
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = filterInteractor.getIndustries()
            if (result.second != null) {
                setState(FilterIndustryScreenState.Error)
            } else {
                if (result.first != null) {
                    setState(FilterIndustryScreenState.Content(result.first!!, checkedIndustry))
                } else {
                    setState(FilterIndustryScreenState.Empty)
                }
            }
        }
    }

    private fun setState(state: FilterIndustryScreenState) {
        stateLiveData.value = state
    }

    fun onIndustryChecked(industry: Industry) {
        if (industry != checkedIndustry) {
            checkedIndustry = industry
            setState(
                FilterIndustryScreenState.Content(
                    (stateLiveData.value as FilterIndustryScreenState.Content).industryList,
                    checkedIndustry
                )
            )
        }
    }

    fun applyFilter() {
        if (/*clickDebounce() &&*/ stateLiveData.value is FilterIndustryScreenState.Content) {
            applyFilterTrigger.value =
                (stateLiveData.value as FilterIndustryScreenState.Content).checkedIndustry
        }
    }
}