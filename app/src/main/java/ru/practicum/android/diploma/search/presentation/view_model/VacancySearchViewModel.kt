package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.domain.models.toHashMap
import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.presentation.FilterMapper
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.FIFTY
import ru.practicum.android.diploma.util.NETWORK_ERROR
import ru.practicum.android.diploma.util.PER_PAGE
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.SERVER_ERROR
import ru.practicum.android.diploma.util.TEXT
import ru.practicum.android.diploma.util.VACANCY_ERROR
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val searchInteractor: VacancySearchInteractor,
    private val filterInteractor: FilterLocalInteractor
) : ViewModel() {

    private var filterParameters: FilterParameters? = null
    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private val stateLiveData = MutableLiveData<VacancyState>()
    private val isFiltered = MutableLiveData<Boolean>(false)
    private val iconVisible = MutableLiveData<Boolean>()
    private val imageCoverIsVisible = MutableLiveData<Boolean>()
    private val filterMap = HashMap<String,String>()
    fun observeCoverImageVisible() = imageCoverIsVisible
    fun observeState(): LiveData<VacancyState> = stateLiveData
    fun observeisFiltered(): LiveData<Boolean> = isFiltered
    fun observeIconVisible(): LiveData<Boolean> = iconVisible

    private val onClickDebounce =
        debounce<Boolean>(SEARCH_DEBOUNCE_DELAY, viewModelScope, false) {
            isClickAllowed = it
        }

    fun searchDebounce(changedText: String, forceButtonClick: Boolean = false) {
        if (changedText.isEmpty()) {
            searchJob?.cancel()
            return
        } else if (latestSearchText == changedText && !forceButtonClick) {
            return
        }
        val searchOption = hashMapOf<String, String>(TEXT to changedText, PER_PAGE to FIFTY)
        searchOption.putAll(filterMap)
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
                    .collect() {
                        processResult(it.data, it.message)
                    }
            }
        }
    }

    private fun processResult(
        vacancyState: VacancyState?,
        message: String?
    ) {
        val vacancies = mutableListOf<Vacancy>()
        vacancyState?.let {
            val list = (vacancyState as VacancyState.Content).vacancy
            vacancies.addAll(list)
        }

        when (message) {
            NETWORK_ERROR -> {
                renderState(VacancyState.Error(errorMessage = NETWORK_ERROR))
            }

            VACANCY_ERROR -> {
                renderState(VacancyState.Empty(message = VACANCY_ERROR))
            }

            SERVER_ERROR -> {
                renderState(VacancyState.Error(errorMessage = SERVER_ERROR))
            }

            else -> {
                renderState(
                    VacancyState.Content(
                        vacancy = vacancies,
                        (vacancyState as VacancyState.Content).count
                    )
                )
            }
        }
    }

    fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            onClickDebounce(true)
        }
        return current
    }

    private fun renderState(state: VacancyState) {
        stateLiveData.postValue(state)
    }

    fun setFocus(textIsEmpty: Boolean) {
        iconVisible.postValue(textIsEmpty)
    }

    fun setVisibleCoverImage(isVisible: Boolean) {
        imageCoverIsVisible.postValue(isVisible)
    }

    fun isFilterButtonEnable() {
        viewModelScope.launch {
            delay(CLICK_DEBOUNCE_DELAY)
            filterParameters = filterInteractor.getFilterParameters()
            isFiltered.postValue(filterParameters != null)
        }
    }

    fun getFilter(): FilterParameters? = filterParameters

    fun forceSearch(filterParameters: FilterParameters?){
        filterMap.clear()
        filterParameters?.let { filterMap.putAll(it.toHashMap()) }
        latestSearchText?.let { searchDebounce(it,true) }
    }
}