package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterLocationViewModel
import ru.practicum.android.diploma.details.presentation.viewmodel.DetailViewModel
import ru.practicum.android.diploma.details.presentation.viewmodel.SimilarViewModel
import ru.practicum.android.diploma.favourites.presentation.viewModel.FavouritesViewModel
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterIndustryViewModel
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.filter.presentation.viewmodel.FilterViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationCountryViewModel
import ru.practicum.android.diploma.filter.presentation.viewmodel.LocationRegionViewModel
import ru.practicum.android.diploma.search.presentation.view_model.VacancySearchViewModel

val viewModelModule = module {
    viewModel {
        DetailViewModel(get(), get (), get())
    }

    viewModel {
        VacancySearchViewModel(get(), get())
    }

    viewModel { (filterParameters: FilterParameters?) ->
        FilterViewModel(filterParameters, get())
    }

    viewModel {(country: Area?, region: Area?) ->
        FilterLocationViewModel(country, region, get(), get())
    }

    viewModel {
        LocationCountryViewModel(get())
    }

    viewModel {(country: Area?) ->
        LocationRegionViewModel(country, get())
    }

    viewModel {(industry: Industry?) ->
        FilterIndustryViewModel(industry, get(), get())
    }

    viewModel {
        FavouritesViewModel(get())
    }

    viewModel {
        SimilarViewModel(get(), get())
    }
}