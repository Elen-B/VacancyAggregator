package ru.practicum.android.diploma.details.data.mapper

import ru.practicum.android.diploma.core.domain.models.Currency
import ru.practicum.android.diploma.core.domain.models.Employer
import ru.practicum.android.diploma.core.domain.models.Employment
import ru.practicum.android.diploma.core.domain.models.Experience
import ru.practicum.android.diploma.core.domain.models.Salary
import ru.practicum.android.diploma.details.data.remote.DetailDto
import ru.practicum.android.diploma.details.data.remote.KeySkill
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

fun DetailDto.mapToProfessionDetail(): ProfessionDetail {
    val employment = if (this.employment.id.isNotEmpty()) Employment(
        this.employment.id,
        this.employment.name
    ) else null

    val employer = if (this.employer.id.isNotEmpty()) Employer(
        this.employer.id,
        this.employer.name,
        this.employer.logoUrls?.original
    ) else null

    val experience = if (!this.experience?.id.isNullOrEmpty()) this.experience?.let {
        Experience(
            it.id,
            this.experience.name
        )
    }
    else null

    val salary = Salary(
        from = this.salary?.from,
        to = this.salary?.to,
        currency = Currency.getCurrency(this.salary?.currency.toString())
    )

    val comment = if (this.contacts != null && this.contacts.phones?.isNotEmpty() == true) {
        this.contacts.phones.first().comment
    } else null

    return ProfessionDetail(
        id = this.id,
        name = this.name,
        employment = employment,
        employer = employer,
        address = this.area.name,
        experience = experience,
        salary = salary,
        description = this.description,
        keySkills = this.keySkills?.mapToString(),
        comment = comment,
        contactName = this.contacts?.name,
        email = this.contacts?.email,
        phone = if (this.contacts != null && this.contacts.phones?.isNotEmpty() == true) {
            convertStringToSting(
                prefix = this.contacts.phones.first().country,
                middle = this.contacts.phones.first().city,
                postfix = this.contacts.phones.first().number
            )
        } else null,
        url = this.url
    )
}

private fun List<KeySkill>?.mapToString(): String {
    return if (this.isNullOrEmpty()) {
        ""
    } else {
        val marker = "• "
        val newline = "\n"
        this.joinToString("") { marker + it.name + newline }
    }
}

private fun convertStringToSting(
    prefix: String?,
    middle: String?,
    postfix: String?
): String? {
    return if (prefix != null && middle != null && postfix != null) {
        "+$prefix ($middle) $postfix"
    } else {
        null
    }
}