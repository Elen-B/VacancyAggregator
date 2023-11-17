package ru.practicum.android.diploma.search.presentation

sealed interface SearchState {
    data class Search(
        val query: String
    ) : SearchState

    data class ForceSearch(
        val forceButton: Boolean = true
    ) : SearchState

    data class Update(
        val forceButton: Boolean = true
    ) : SearchState
}