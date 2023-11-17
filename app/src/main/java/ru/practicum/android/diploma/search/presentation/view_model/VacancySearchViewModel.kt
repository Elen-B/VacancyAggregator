package ru.practicum.android.diploma.search.presentation.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.filter.domain.api.FilterLocalInteractor
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.filter.domain.models.toHashMap
import ru.practicum.android.diploma.search.domain.VacancySearchInteractor
import ru.practicum.android.diploma.search.presentation.SearchState
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.PAGE
import ru.practicum.android.diploma.util.PER_PAGE
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.TEXT
import ru.practicum.android.diploma.util.TWENTY
import ru.practicum.android.diploma.util.ZERO
import ru.practicum.android.diploma.util.debounce

class VacancySearchViewModel(
    private val searchInteractor: VacancySearchInteractor,
    private val filterInteractor: FilterLocalInteractor
) : ViewModel() {

    private var filterParameters: FilterParameters? = null
    private var latestSearchText: String = ""
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private val stateLiveData = MutableLiveData<VacancyState>()
    private val isFiltered = MutableLiveData(false)
    private val iconVisible = MutableLiveData<Boolean>()
    private val imageCoverIsVisible = MutableLiveData<Boolean>()
    private val filterMap = HashMap<String, String>()
    private var lastState: VacancyState.Content? = null
    fun observeCoverImageVisible() = imageCoverIsVisible
    fun observeState(): LiveData<VacancyState> = stateLiveData
    fun observeisFiltered(): LiveData<Boolean> = isFiltered
    fun observeIconVisible(): LiveData<Boolean> = iconVisible

    init {
        isFilterButtonEnable()
    }

    private val onClickDebounce =
        debounce<Boolean>(SEARCH_DEBOUNCE_DELAY, viewModelScope, false) {
            isClickAllowed = it
        }

    fun searchDebounce(
        searchState: SearchState
    ) {

        val searchOption = hashMapOf<String, String>(
            TEXT to latestSearchText,
            PER_PAGE to TWENTY,
            PAGE to ZERO.toString()
        )
        searchOption.putAll(filterMap)
        when (searchState) {
            is SearchState.Search -> {
                if (searchState.query.isEmpty()) {
                    searchJob?.cancel()
                    latestSearchText = ""
                    return
                } else if (searchState.query == latestSearchText) {
                    return
                } else {
                    searchOption.put(TEXT, searchState.query)
                    latestSearchText = searchState.query
                }
            }

            is SearchState.ForceSearch -> renderState(VacancyState.Loading)
            is SearchState.Update -> searchOption.put(
                PAGE,
                (getState()?.page?.toInt()?.plus(1)).toString()
            )
        }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (searchState is SearchState.Search) {
                delay(SEARCH_DEBOUNCE_DELAY)
                renderState(VacancyState.Loading)
            }
            searchRequest(searchOption)
        }
    }

    private suspend fun searchRequest(searchOptions: HashMap<String, String>) {
        searchInteractor
            .searchVacancy(searchOptions)
            .first().data?.let { renderState(it) }
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
            filterParameters = filterInteractor.getFilterParameters()
            filterMap.clear()
            filterParameters?.toHashMap()?.let { filterMap.putAll(it) }
            isFiltered.postValue(filterParameters != null)
        }
    }

    fun getFilter(): FilterParameters? = filterParameters

    fun forceSearch(filterParameters: FilterParameters?) {
        filterParameters?.let {
            filterMap.clear()
            filterMap.putAll(it.toHashMap())
        }
        latestSearchText?.let {
            searchDebounce(SearchState.ForceSearch())
        }
    }

    fun setState(state: VacancyState.Content) {
        lastState = state
    }

    fun getState(): VacancyState.Content? = lastState
    fun stateIsNotEmpty(): Boolean =
        lastState?.let { it.pages.toInt() - it.page.toInt() != 1 } == true

}