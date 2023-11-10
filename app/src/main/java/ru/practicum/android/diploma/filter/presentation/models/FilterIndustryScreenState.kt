package ru.practicum.android.diploma.filter.presentation.models

import ru.practicum.android.diploma.core.domain.models.Industry

sealed interface FilterIndustryScreenState {
    object Empty : FilterIndustryScreenState

    object Error : FilterIndustryScreenState

    data class Content(val industryList: List<Industry>, val checkedIndustry: Industry?) :
        FilterIndustryScreenState
}