package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.search.presentation.FilterMapper
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.VACANCY_ERROR

class VacancySearchViewModel(
    private val searchInteractor: VacancySearchInteractor,
    private val filterInteractor: FilterLocalInteractor
) : ViewModel() {

    private var filterParameters: FilterParameters? = null
    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private val stateLiveData = MutableLiveData<VacancyState>()
    private val foundVacanciesCount = MutableLiveData<String?>(null)
    private val isFiltered = MutableLiveData<Boolean>(false)
    private val iconVisible = MutableLiveData<Boolean>()
    private val imageCoverIsVisible = MutableLiveData<Boolean>()
    fun observeCoverImageVisible() = imageCoverIsVisible
    fun observeState(): LiveData<VacancyState> = stateLiveData
    fun observeFoundVacanciesCount(): LiveData<String?> = foundVacanciesCount
    fun observeisFiltered(): LiveData<Boolean> = isFiltered
    fun observeIconVisible(): LiveData<Boolean> = iconVisible

    fun searchDebounce(changedText: String, forceButtonClick: Boolean = false) {
        if(changedText.isEmpty()){
            searchJob?.cancel()
            return
        }
        else if (latestSearchText == changedText && !forceButtonClick) {
            return
        }
        val searchOption = hashMapOf<String, String>("text" to changedText)
        if (filterParameters != null) {
            searchOption.putAll(FilterMapper.getMap(filterParameters!!))
        }
        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (!forceButtonClick) {
                delay(SEARCH_DEBOUNCE_DELAY)
            }
            searchRequest(searchOption)
        }
    }

    private fun searchRequest(searchOptions: HashMap<String, String>) {
        if (searchOptions.isNotEmpty()) {
            renderState(VacancyState.Loading)
            viewModelScope.launch {
                searchInteractor
                    .searchVacancy(searchOptions)
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

            message == VACANCY_ERROR || foundVacancies.isNullOrEmpty() -> {
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

    fun isFilterButtonEnable() {
        viewModelScope.launch {
            delay(CLICK_DEBOUNCE_DELAY)
            filterParameters = filterInteractor.getFilterParameters()
            isFiltered.postValue(filterParameters != null)
        }
    }

    fun getFilter(): FilterParameters? = filterParameters

    fun setFocus(textIsEmpty: Boolean) {
        iconVisible.postValue(textIsEmpty)
    }

    fun setVisibleCoverImage(isVisible: Boolean){
        imageCoverIsVisible.postValue(isVisible)
    }
}