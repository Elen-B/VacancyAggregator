package ru.practicum.android.diploma.filter.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Industry

@Parcelize
data class FilterParameters(
    var country: Area? = null,
    var region: Area? = null,
    var industry: Industry? = null,
    var salary: Int? = null,
    var fSalaryRequired: Boolean = false
) : Parcelable {
    fun isEmpty(): Boolean {
        return this.country == null && this.region == null && this.industry == null && this.salary == null && !this.fSalaryRequired
    }
}
