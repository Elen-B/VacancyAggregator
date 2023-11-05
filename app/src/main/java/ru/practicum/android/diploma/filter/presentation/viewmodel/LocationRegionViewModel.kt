package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.models.LocationRegionScreenState

class LocationRegionViewModel(country: Area?, private val filterInteractor: FilterInteractor) :
    ViewModel() {

    private val stateLiveData = MutableLiveData<LocationRegionScreenState>()
    fun observeState(): LiveData<LocationRegionScreenState> = stateLiveData

    init {
        loadData(country = country)
    }

    private fun loadData(country: Area?) {
        viewModelScope.launch {
            val id = country?.id.orEmpty()
            //Log.e("filter", "id = " + id)
            val result = filterInteractor.getAreas(id)
            //Log.e("filter", result.toString() + result.toString())
            if (!result.second.isNullOrEmpty()) {
                setState(LocationRegionScreenState.Error)
            } else {
                if (result.first != null) {
                    setState(LocationRegionScreenState.Content(result.first!!))
                } else {
                    setState(LocationRegionScreenState.Error)
                }
            }
        }
    }

    private fun setState(state: LocationRegionScreenState) {
        stateLiveData.value = state
    }
}