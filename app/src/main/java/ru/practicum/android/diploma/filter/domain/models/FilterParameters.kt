package ru.practicum.android.diploma.filter.domain.models

data class FilterParameters(
    var country: Area? = null,
    var region: Area? = null,
    var industry: Industry? = null,
    var salary: Int? = null,
    var fSalaryRequired: Boolean = false
) {
    fun isEmpty(): Boolean {
        return this.country == null && this.region == null && this.industry == null && this.salary == null && !this.fSalaryRequired
    }
}
