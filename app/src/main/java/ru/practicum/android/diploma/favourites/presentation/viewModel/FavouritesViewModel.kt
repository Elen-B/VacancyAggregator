package ru.practicum.android.diploma.favourites.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.presentation.model.FavouritesState
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class FavouritesViewModel(private val favouritesInteractor: FavouritesInteractor): ViewModel() {

    private val favouritesLiveDataMutable = MutableLiveData<FavouritesState>()
    fun observeState(): LiveData<FavouritesState> = favouritesLiveDataMutable

    fun loadFavouriteVacancyList(){
        viewModelScope.launch {
            favouritesInteractor.getListVacancy().collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(vacancyList: List<SearchVacancy>?, message: String?) {
        when (message) {
            ERROR -> {
                renderState(FavouritesState.Error)
            }
            EMPTY -> {
                renderState(FavouritesState.Empty)
            }
            else -> {
                val currentVacancyList = mutableListOf<SearchVacancy>().also {
                    it.addAll(vacancyList!!)
                }
                renderState(FavouritesState.Content(currentVacancyList))
            }
        }
    }

    private fun renderState(state: FavouritesState) {
        favouritesLiveDataMutable.postValue(state)
    }

    companion object {
        private const val ERROR = "error"
        private const val EMPTY = "empty"
    }
}