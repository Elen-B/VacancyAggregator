package ru.practicum.android.diploma.favourites.presentation.state

import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

sealed interface FavouritesState {
    object Loading : FavouritesState
    object Empty : FavouritesState
    object Error : FavouritesState
    data class Content(val vacancyList: List<ProfessionDetail>) : FavouritesState
}