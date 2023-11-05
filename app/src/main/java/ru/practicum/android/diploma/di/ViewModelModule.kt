package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterLocationViewModel
import ru.practicum.android.diploma.details.presentation.DetailViewModel
import ru.practicum.android.diploma.filter.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationCountryViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationRegionViewModel
import ru.practicum.android.diploma.search.presentation.view_model.VacancySearchViewModel

val viewModelModule = module {
    viewModel {
        DetailViewModel(get())
    }

    viewModel {
        VacancySearchViewModel(get())
    }

    viewModel {
        FilterViewModel()
    }

    viewModel {(country: Area?, region: Area?) ->
        FilterLocationViewModel(country, region)
    }

    viewModel {
        LocationCountryViewModel(get())
    }

    viewModel {
        LocationRegionViewModel()
    }
}