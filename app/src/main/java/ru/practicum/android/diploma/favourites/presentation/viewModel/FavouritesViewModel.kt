package ru.practicum.android.diploma.favourites.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.favourites.presentation.model.FavouritesState

class FavouritesViewModel(): ViewModel() {

    private val favouritesLiveDataMutable = MutableLiveData<FavouritesState>()
    fun observeState(): LiveData<FavouritesState> = favouritesLiveDataMutable

    fun loadFavouriteVacancyList(){
        viewModelScope.launch {
            //TODO
        }
    }

    private fun processResult(vacancyList: Any, message: String) {
        when (message) {
            ERROR -> {
                renderState(FavouritesState.Error)
            }
            EMPTY -> {
                renderState(FavouritesState.Empty)
            }
            else -> {
                val currentVacancyList = mutableListOf<Any>().also {
                    it.addAll(listOf(vacancyList))
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