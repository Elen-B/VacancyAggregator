package ru.practicum.android.diploma.favourites.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.presentation.state.FavouritesState

class FavouritesViewModel(private val favouritesInteractor: FavouritesInteractor) : ViewModel() {

    private val favouritesLiveDataMutable = MutableLiveData<FavouritesState>()
    fun observeState(): LiveData<FavouritesState> = favouritesLiveDataMutable

    fun loadFavouriteVacancyList() {
        setState(FavouritesState.Loading)
        viewModelScope.launch {
            favouritesInteractor.getListVacancy().collect { pair ->
                processResult(pair.first, pair.second)
            }
        }
    }

    private fun processResult(vacancyList: List<ProfessionDetail>?, message: String?) {

        when (message) {
            ERROR -> {
                setState(FavouritesState.Error)
            }

            EMPTY -> {
                setState(FavouritesState.Empty)
            }

            else -> {
                if (vacancyList != null) {
                    setState(FavouritesState.Content(vacancyList))
                }
            }
        }
    }

    private fun setState(state: FavouritesState) {
        favouritesLiveDataMutable.postValue(state)
    }

    companion object {
        const val ERROR = "error"
        const val EMPTY = "empty"
    }
}