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
import ru.practicum.android.diploma.util.CLICK_DEBOUNCE_DELAY
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SingleEventLiveData
import ru.practicum.android.diploma.util.UNKNOWN_ERROR
import ru.practicum.android.diploma.util.debounce
import ru.practicum.android.diploma.util.VACANCY_ID

class DetailViewModel (
    private val detailsInterActor: DetailsInterActor,
    private val savedStateHandle: SavedStateHandle,
    private val favouritesInteractor: FavouritesInteractor
) : ViewModel() {
    private val _state = MutableLiveData<DetailState>(DetailState.Loading)
    val state = _state

    private val inFavouritesLiveDataMutable = MutableLiveData(false)
    fun observeStateInFavourites(): LiveData<Boolean>  = inFavouritesLiveDataMutable

    private val shareVacancyTrigger = SingleEventLiveData<String>()
    fun getShareVacancyTrigger(): LiveData<String> = shareVacancyTrigger

    private val showSimilarVacanciesTrigger = SingleEventLiveData<String>()
    fun getShowSimilarVacanciesTrigger(): LiveData<String> = showSimilarVacanciesTrigger

    private var isClickAllowed = true
    private val onClickDebounce =
        debounce<Boolean>(CLICK_DEBOUNCE_DELAY, viewModelScope, false) {
            isClickAllowed = it
        }

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>(VACANCY_ID) ?: return@launch
            val inFavourites = favouritesInteractor.inFavourites(id)
            when (val resultData = detailsInterActor.getDetails(id = id)) {
                is Resource.Error -> {
                    if (inFavourites) {
                        try {
                            val vacancy = favouritesInteractor.getVacancyById(id)
                            if (vacancy != null)
                                _state.value = DetailState.Success(vacancy, true)
                            else
                                _state.value =
                                    resultData.message?.let {
                                        DetailState.Error(
                                            message = it,
                                        )
                                    }
                        } catch (_: Exception) {
                            _state.value =
                                resultData.message?.let {
                                    DetailState.Error(
                                        message = it,
                                    )
                                }
                        }
                    } else {
                        _state.value =
                            resultData.message?.let {
                                DetailState.Error(
                                    message = it,
                                )
                            }
                    }
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

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            onClickDebounce(true)
        }
        return current
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

    fun shareVacancy(url: String) {
        if (clickDebounce()) {
            shareVacancyTrigger.value = url
        }
    }

    fun showSimilarVacancies(id: String) {
        if (clickDebounce()) {
            showSimilarVacanciesTrigger.value = id
        }
    }
}