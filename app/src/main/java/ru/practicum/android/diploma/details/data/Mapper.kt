package ru.practicum.android.diploma.details.data

import ru.practicum.android.diploma.details.data.remote.DetailDto
import ru.practicum.android.diploma.details.data.remote.KeySkill
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

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
        phone = "+${this.contacts?.phones?.first()?.country} (${this.contacts?.phones?.first()?.city}) ${this.contacts?.phones?.first()?.number}",
        phoneNumber = convertStringToLong(
            prefix = this.contacts?.phones?.first()?.country,
            middle = this.contacts?.phones?.first()?.city,
            postfix = this.contacts?.phones?.first()?.number
        )
    )

private fun List<KeySkill>?.mapToString(): String {
    return if (this.isNullOrEmpty()) {
        ""
    } else {
        val marker = "• "
        val newline = "\n"
        this.joinToString("") { marker + it.name + newline }
    }
}

private fun convertStringToLong(
    prefix:String?,
    middle:String?,
    postfix: String?
): Long? {
    val postfixRemoved = postfix?.replace("-", "")
    return try {
        (prefix+middle+postfixRemoved).toLong()
    } catch (error:Exception) {
        null
    }
}