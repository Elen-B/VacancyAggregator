package ru.practicum.android.diploma.filter.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.filter.presentation.models.LocationRegionScreenState

class LocationRegionViewModel(private val filterInteractor: FilterInteractor): ViewModel() {

    private val stateLiveData = MutableLiveData<LocationRegionScreenState>()
    fun observeState(): LiveData<LocationRegionScreenState> = stateLiveData

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            val id: String? = "113"
            val result = filterInteractor.getAreas(id.orEmpty())
            Log.e("filter", result.toString())
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