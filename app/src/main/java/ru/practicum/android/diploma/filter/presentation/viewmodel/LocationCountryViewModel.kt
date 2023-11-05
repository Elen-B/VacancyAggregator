package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.presentation.models.LocationCountryScreenState

class LocationCountryViewModel(private val filterInteractor: FilterInteractor): ViewModel()  {

    private val stateLiveData = MutableLiveData<LocationCountryScreenState>()
    fun observeState(): LiveData<LocationCountryScreenState> = stateLiveData

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val result = filterInteractor.getCountries()
            if (result.second!=null) {
                setState(LocationCountryScreenState.Error)
            } else {
                if (result.first != null) {
                    setState(LocationCountryScreenState.Content(result.first!!))
                } else {
                    setState(LocationCountryScreenState.Error)
                }
            }

        }
    }

    private fun setState(state: LocationCountryScreenState) {
        stateLiveData.value = state
    }
}