package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.domain.models.FilterSaveMode
import ru.practicum.android.diploma.filter.presentation.state.FilterIndustryScreenState
import ru.practicum.android.diploma.util.SingleEventLiveData

class FilterIndustryViewModel(
    industry: Industry?,
    private val filterInteractor: FilterInteractor,
    private val filterLocalInteractor: FilterLocalInteractor
) : ViewModel() {

    private val originalList: MutableList<Industry> = ArrayList()
    private val filteredList: MutableList<Industry> = ArrayList()
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
                    originalList.addAll(result.first!!)
                    setState(FilterIndustryScreenState.Content(originalList, checkedIndustry))
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

    fun onEditTextChanged(searchQuery: String?) {
        if (stateLiveData.value is FilterIndustryScreenState.Error)
            return

        filteredList.clear()
        if (searchQuery.isNullOrEmpty()) {
            setState(FilterIndustryScreenState.Content(originalList, checkedIndustry))
        } else {
            for (item in originalList) {
                if (item.name.contains(searchQuery, true)) {
                    filteredList.add(item)
                }
            }
            if (filteredList.size > 0) {
                setState(FilterIndustryScreenState.Content(filteredList, checkedIndustry))
            } else {
                setState(FilterIndustryScreenState.Empty)
            }
        }
    }

    fun applyFilter() {
        if (stateLiveData.value is FilterIndustryScreenState.Content) {
            val checkedIndustry =
                (stateLiveData.value as FilterIndustryScreenState.Content).checkedIndustry
            filterLocalInteractor.saveFilterParameters(
                FilterParameters(industry = checkedIndustry),
                FilterSaveMode.INDUSTRY
            )
            applyFilterTrigger.value = checkedIndustry
        }
    }
}