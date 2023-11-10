package ru.practicum.android.diploma.details.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.details.domain.api.DetailsInterActor
import ru.practicum.android.diploma.details.presentation.models.DetailState
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.search.domain.models.SearchVacancy
import ru.practicum.android.diploma.util.Resource

class DetailViewModel (
    private val detailsInterActor: DetailsInterActor,
    private val savedStateHandle: SavedStateHandle,
    private val favouritesInteractor: FavouritesInteractor
) : ViewModel() {
    private val _state = MutableLiveData<DetailState>(DetailState.Loading)
    val state = _state

    private var inFavouritesLiveDataMutable = MutableLiveData<Boolean>()
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
        }
    }
    //Проверяем есть ли вакансия в избраанных
    fun inFavourites(id: String){
        viewModelScope.launch {
            inFavouritesLiveDataMutable.value = favouritesInteractor.inFavourites(id)
        }
    }
    //Добавляем или удаляем вакансию из избранных
    fun addFavourites(vacancy: SearchVacancy, isFavourites: Boolean){
        viewModelScope.launch {
            if (isFavourites) favouritesInteractor.insertVacancy(vacancy)
            else favouritesInteractor.deleteVacancy(vacancy)
        }
    }
}