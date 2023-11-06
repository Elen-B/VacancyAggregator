package ru.practicum.android.diploma.search.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.VACANCY_ERROR

class VacancySearchViewModel(private val interactor: VacancySearchInteractor) : ViewModel() {

    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private val stateLiveData = MutableLiveData<VacancyState>()
    private val foundVacanciesCount = MutableLiveData<String?>(null)
    fun observeState(): LiveData<VacancyState> = stateLiveData
    fun observeFoundVacanciesCount(): LiveData<String?> = foundVacanciesCount

    fun searchDebounce(changedText: String, forceButtonClick: Boolean = false) {
        if (latestSearchText == changedText && !forceButtonClick) {
            return
        }
        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if(!forceButtonClick){
            delay(SEARCH_DEBOUNCE_DELAY)}
            searchRequest(changedText)
        }
    }

    private fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {
            renderState(VacancyState.Loading)
            viewModelScope.launch {
                interactor
                    .searchVacancy(newSearchText)
                    .collect { pair ->
                        processResult(
                            pair.second.data,
                            pair.second.message
                        ).apply { foundVacanciesCount.postValue(pair.first) }
                    }
            }
        }
    }

    private fun processResult(
        foundVacancies: List<SearchVacancy>?,
        message: String?
    ) {
        val vacancies = mutableListOf<SearchVacancy>()
        if (foundVacancies != null) {
            vacancies.addAll(foundVacancies)
        }
        when {
            message == NETWORK_ERROR -> {
                renderState(VacancyState.Error(errorMessage = NETWORK_ERROR))
            }

            message == SERVER_ERROR -> {
                renderState(VacancyState.Error(errorMessage = SERVER_ERROR))
            }

            vacancies.isNullOrEmpty() -> {
                renderState(VacancyState.Empty(message = VACANCY_ERROR))
            }

            else -> {
                renderState(VacancyState.Content(vacancy = vacancies))
            }
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            viewModelScope.launch {
                delay(CLICK_DEBOUNCE_DELAY)
                isClickAllowed = true
            }
        }
        return current
    }

    private fun renderState(state: VacancyState) {
        stateLiveData.postValue(state)
    }
}