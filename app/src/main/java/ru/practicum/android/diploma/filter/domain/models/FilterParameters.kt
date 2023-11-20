package ru.practicum.android.diploma.filter.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import ru.practicum.android.diploma.core.domain.models.Area
import ru.practicum.android.diploma.core.domain.models.Industry
import ru.practicum.android.diploma.util.AREA
import ru.practicum.android.diploma.util.INDUSTRY
import ru.practicum.android.diploma.util.ONLY_WITH_SALARY
import ru.practicum.android.diploma.util.SALARY

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

internal fun FilterParameters.toHashMap(): HashMap<String, String> {
    return hashMapOf<String, String>().apply {
        country?.id?.let {
            put(AREA, it)
        }
        region?.id?.let {
            put(AREA, it)
        }
        industry?.id?.let {
            put(INDUSTRY, it)
        }
        salary?.let {
            put(SALARY, it.toString())
        }
        fSalaryRequired?.let {
            put(ONLY_WITH_SALARY, it.toString())
        }
    }
}


