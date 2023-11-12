package ru.practicum.android.diploma.details.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.domain.api.DetailsInterActor
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.details.presentation.state.DetailState
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.util.Resource

class DetailViewModel (
    private val detailsInterActor: DetailsInterActor,
    private val savedStateHandle: SavedStateHandle,
    private val favouritesInteractor: FavouritesInteractor
) : ViewModel() {
    private val _state = MutableLiveData<DetailState>(DetailState.Loading)
    val state = _state

    private val inFavouritesLiveDataMutable = MutableLiveData(false)
    fun observeStateInFavourites(): LiveData<Boolean>  = inFavouritesLiveDataMutable

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>("id") ?: return@launch
            when (val resultData = detailsInterActor.getDetails(id = id)) {
                is Resource.Error -> {
                    _state.value =
                        DetailState.Error(
                            message = resultData.message ?: "An unknown error",)
                }
                is Resource.Success -> {
                    _state.value =
                        resultData.data?.let {
                            DetailState.Success(data = it)
                        }
                }
            }
            if (_state.value is DetailState.Success) {
                setFavouriteState(favouritesInteractor.inFavourites(id))
            } else {
                setFavouriteState(false)
            }
        }
    }

    private fun setFavouriteState(isFavourite: Boolean) {
        inFavouritesLiveDataMutable.value = isFavourite
    }

    private fun insertFavouriteVacancy(vacancy: ProfessionDetail) {
        viewModelScope.launch {
            favouritesInteractor.insertVacancy(vacancy)
        }
    }

    private fun deleteFavouriteVacancy(vacancy: ProfessionDetail) {
        viewModelScope.launch {
            favouritesInteractor.deleteVacancy(vacancy)
        }
    }

    fun onFavouriteClick() {
        inFavouritesLiveDataMutable.value = !(inFavouritesLiveDataMutable.value ?: true)
        if (state.value is DetailState.Success) {
            val vacancy = (state.value as DetailState.Success).data
            if (inFavouritesLiveDataMutable.value == true) {
                insertFavouriteVacancy(vacancy)
            } else {
                deleteFavouriteVacancy(vacancy)
            }
        }
    }
}