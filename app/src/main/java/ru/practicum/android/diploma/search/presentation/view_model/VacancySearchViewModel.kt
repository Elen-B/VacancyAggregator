package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY

class VacancySearchViewModel : ViewModel(){

    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private val stateLiveData = MutableLiveData<VacancyState>()
    fun observeState(): LiveData<VacancyState> = stateLiveData

    fun searchDebounce(changedText: String, updateBt: Boolean) {
        if (latestSearchText == changedText && !updateBt) {
            return
        }
        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_DELAY)
            searchRequest(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(VacancyState.Loading)
            viewModelScope.launch {
                //TODO
            }
        }
    }

    private fun renderState(state: VacancyState) {
        stateLiveData.postValue(state)
    }
}