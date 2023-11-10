package ru.practicum.android.diploma.search.presentation.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.singleOrNull
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
    private val filterInteractor: FilterLocalInteractor) : ViewModel() {
    private var filterParameters: FilterParameters? = null
    private var latestSearchText: String? = null
    private var searchJob: Job? = null
    private var isClickAllowed = true
    private val stateLiveData = MutableLiveData<VacancyState>()
    private val foundVacanciesCount = MutableLiveData<String?>(null)
    private val isFiltered = MutableLiveData<Boolean>(false)
    private val vacanciesList = MutableLiveData<PagingData<SearchVacancy>>()
    fun observeState(): LiveData<VacancyState> = stateLiveData
    fun observeFoundVacanciesCount(): LiveData<String?> = foundVacanciesCount
    fun observeisFiltered(): LiveData<Boolean> = isFiltered
    fun observeVacanciesList(): LiveData<PagingData<SearchVacancy>> = vacanciesList
    fun getVacancies(option: HashMap<String,String> = HashMap()): LiveData<PagingData<SearchVacancy>> {

        return searchInteractor.searchVacancy(option).asLiveData()

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

    fun isFilterButtonEnable(){
        viewModelScope.launch {
            delay(CLICK_DEBOUNCE_DELAY)
            filterParameters = filterInteractor.getFilterParameters()
            isFiltered.postValue(filterParameters != null)
        }
    }

    fun getFilter(): FilterParameters? = filterParameters
}