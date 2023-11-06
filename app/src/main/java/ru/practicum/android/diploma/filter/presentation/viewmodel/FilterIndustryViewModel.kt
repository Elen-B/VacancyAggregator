package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.presentation.models.FilterIndustryScreenState

class FilterIndustryViewModel(private val filterInteractor: FilterInteractor): ViewModel() {

    private val stateLiveData = MutableLiveData<FilterIndustryScreenState>()
    fun observeState(): LiveData<FilterIndustryScreenState> = stateLiveData

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = filterInteractor.getIndustries()
            //Log.e("filter", result.toString() + result.toString())
            if (!result.second.isNullOrEmpty()) {
                setState(FilterIndustryScreenState.Error)
            } else {
                if (result.first != null) {
                    setState(FilterIndustryScreenState.Content(result.first!!, null))
                } else {
                    setState(FilterIndustryScreenState.Error)
                }
            }
        }
    }

    private fun setState(state: FilterIndustryScreenState) {
        stateLiveData.value = state
    }
}