package ru.practicum.android.diploma.favourites.presentation.model

sealed class FavouritesState{
    object Empty: FavouritesState()
    object Error: FavouritesState()
    data class Content(val vacancyList: Any): FavouritesState()
}