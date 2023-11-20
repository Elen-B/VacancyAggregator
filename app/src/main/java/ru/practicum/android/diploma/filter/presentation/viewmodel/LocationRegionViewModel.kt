package ru.practicum.android.diploma.filter.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterInteractor
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.state.LocationRegionScreenState

class LocationRegionViewModel(country: Area?, private val filterInteractor: FilterInteractor) :
    ViewModel() {

    private val stateLiveData = MutableLiveData<LocationRegionScreenState>()
    fun observeState(): LiveData<LocationRegionScreenState> = stateLiveData

    private val originalList: MutableList<Area> = ArrayList()
    private val filteredList: MutableList<Area> = ArrayList()

    init {
        loadData(country = country)
    }

    private fun loadData(country: Area?) {
        viewModelScope.launch {
            val id = country?.id.orEmpty()
            val result = filterInteractor.getAreas(id)
            if (result.isError) {
                setState(LocationRegionScreenState.Error)
            } else {
                if (result.data != null) {
                    originalList.addAll(result.data)
                    setState(LocationRegionScreenState.Content(originalList))
                } else {
                    setState(LocationRegionScreenState.Error)
                }
            }
        }
    }

    private fun setState(state: LocationRegionScreenState) {
        stateLiveData.value = state
    }

    fun onEditTextChanged(searchQuery: String?) {
        if (stateLiveData.value is LocationRegionScreenState.Error)
            return

        filteredList.clear()
        if (searchQuery.isNullOrEmpty()) {
            setState(LocationRegionScreenState.Content(originalList))
        } else {
            for (item in originalList) {
                if (item.name.contains(searchQuery, true)) {
                    filteredList.add(item)
                }
            }
            if (filteredList.size > 0) {
                setState(LocationRegionScreenState.Content(filteredList))
            } else {
                setState(LocationRegionScreenState.Empty)
            }
        }
    }
}