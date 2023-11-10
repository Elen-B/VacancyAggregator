package ru.practicum.android.diploma.favourites.presentation.state

import ru.practicum.android.diploma.search.domain.models.SearchVacancy

sealed class FavouritesState{
    object Empty: FavouritesState()
    object Error: FavouritesState()
    data class Content(val vacancyList: List<SearchVacancy>): FavouritesState()
}