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
import ru.practicum.android.diploma.search.presentation.VacancyState
import ru.practicum.android.diploma.util.PAGE
import ru.practicum.android.diploma.util.PER_PAGE
import ru.practicum.android.diploma.util.SEARCH_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.TEXT
import ru.practicum.android.diploma.util.TWENTY
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
    private val isFiltered = MutableLiveData(false)
    private val iconVisible = MutableLiveData<Boolean>()
    private val imageCoverIsVisible = MutableLiveData<Boolean>()
    private val filterMap = HashMap<String, String>()
    private var pageNumber: Int = 0
    private var lastPage: Boolean = true
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
        changedText: String = "",
        forceButtonClick: Boolean = false,
        update: Boolean = false
    ) {
        if (changedText.isEmpty() && !update) {
            latestSearchText = ""
            searchJob?.cancel()
            return
        } else if (latestSearchText == changedText && !forceButtonClick && !update) {
            return
        }

        val searchOption = hashMapOf(TEXT to changedText, PER_PAGE to TWENTY, PAGE to "0")
        if (update) {
            pageNumber++
            searchOption.put(PAGE, pageNumber.toString())
        } else {
            pageNumber = 0
        }
        searchOption.putAll(filterMap)
        latestSearchText = changedText

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            if (!forceButtonClick) {
                delay(SEARCH_DEBOUNCE_DELAY)
            }
            searchRequest(searchOption, update)
        }
    }

    private fun searchRequest(searchOptions: HashMap<String, String>, isUpdate: Boolean = false) {
        if (searchOptions.isNotEmpty()) {
            if (!isUpdate) {
                lastPage = true
                renderState(VacancyState.Loading)
            }
            viewModelScope.launch {
                searchInteractor
                    .searchVacancy(searchOptions)
                    .first().data?.let { renderState(it) }
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
        updateLocalData(state)
        stateLiveData.postValue(state)
    }

    private fun updateLocalData(state: VacancyState) {
        lastPage = when (state) {
            is VacancyState.Content -> state.lastPage
            is VacancyState.Update -> state.lastPage
            else -> lastPage
        }
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
            searchDebounce(it, true) }
    }

    fun isLastPage(): Boolean = lastPage
    fun getPage(): Int = pageNumber

}