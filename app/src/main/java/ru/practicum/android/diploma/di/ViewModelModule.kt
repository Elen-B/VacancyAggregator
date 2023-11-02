package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.details.presentation.DetailViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.search.presentation.view_model.VacancySearchViewModel

val viewModelModule = module {
    viewModel {
        FilterViewModel()
        DetailViewModel(get())
        VacancySearchViewModel()
    }
}