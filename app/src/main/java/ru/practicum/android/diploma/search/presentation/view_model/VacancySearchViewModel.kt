package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY

class VacancySearchViewModel(private val interactor: VacancySearchInteractor) : ViewModel() {

    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private val stateLiveData = MutableLiveData<VacancyState>()
    private val foundVacanciesCount = MutableLiveData<String>("0")
    fun observeState(): LiveData<VacancyState> = stateLiveData
    fun observeFoundVacanciesCount(): LiveData<String> = foundVacanciesCount

    fun searchDebounce(changedText: String, forceButtonClick: Boolean = false) {
        if (latestSearchText == changedText && !forceButtonClick) {
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
                interactor
                    .searchVacancy(newSearchText)
                    .collect { pair ->
                        processResult(pair.data, pair.message)
                    }
            }
        }
    }

    private fun processResult(foundVacancies: List<SearchVacancy>?, message: String?) {
        val vacancies = mutableListOf<SearchVacancy>()
        if (foundVacancies != null) {
            vacancies.addAll(foundVacancies)
        }
        when {
            message == R.string.network_error.toString() -> {
                renderState(VacancyState.Error(errorMessage = R.string.network_error.toString()))
            }

            message == R.string.vacancy_error.toString() -> {
                renderState(VacancyState.Empty(message = R.string.vacancy_error.toString()))
            }

            else -> {
                renderState(VacancyState.Content(vacancy = vacancies))
                foundVacanciesCount.postValue(message.toString())
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