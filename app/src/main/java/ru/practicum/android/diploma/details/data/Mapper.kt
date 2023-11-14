package ru.practicum.android.diploma.details.data

import ru.practicum.android.diploma.details.data.remote.DetailDto
import ru.practicum.android.diploma.details.data.remote.KeySkill
import ru.practicum.android.diploma.details.data.remote.SimilarVacancyDto
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail
import ru.practicum.android.diploma.details.domain.models.ProfessionSimillar

fun DetailDto.mapToProfessionDetail(): ProfessionDetail =
    ProfessionDetail(
        id = this.id,
        name = this.name,
        employmentId = this.employment.id,
        employmentName = this.employment.name,
        employerId = this.employer.id,
        employerName = this.employer.name,
        employerLogo = this.employer.logoUrls?.original,
        experienceId = this.experience?.id,
        experienceName = this.experience?.name,
        salaryCurrency = this.salary?.currency,
        salaryFrom = this.salary?.from,
        salaryTo = this.salary?.to,
        employerCity = this.area.name,
        description = this.description,
        keySkills = this.keySkills?.mapToString(),
        comment = this.contacts?.phones?.first()?.comment,
        contactName = this.contacts?.name,
        email = this.contacts?.email,
        phone = if (this.contacts == null || this.contacts.phones.isEmpty()) null else {
            convertStringToSting(
                prefix = this.contacts.phones.first().country,
                middle = this.contacts.phones.first().city,
                postfix = this.contacts.phones.first().number
            )
        },
        url = this.url
    )


fun SimilarVacancyDto.mapToProfessionSimilar(): List<ProfessionSimillar> {
    return this.items.map {
        ProfessionSimillar(
            employerId = it.employer.id,
            employerName = it.employer.name,
            employerLogo = it.employer.logoUrls?.original,
            id = it.id,
            name = it.name,
            city = it.area?.name,
            salaryCurrency = it.salary?.currency,
            salaryFrom = it.salary?.from,
            salaryTo = it.salary?.to
        )
    }
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