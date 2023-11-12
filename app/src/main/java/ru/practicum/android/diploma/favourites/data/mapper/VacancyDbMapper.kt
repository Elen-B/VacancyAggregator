package ru.practicum.android.diploma.favourites.data.mapper

import ru.practicum.android.diploma.core.db.entity.EmployerEntity
import ru.practicum.android.diploma.core.db.entity.EmploymentEntity
import ru.practicum.android.diploma.core.db.entity.ExperienceEntity
import ru.practicum.android.diploma.core.db.entity.VacancyEntity
import ru.practicum.android.diploma.core.domain.models.Currency
import ru.practicum.android.diploma.core.domain.models.Salary
import ru.practicum.android.diploma.details.domain.models.ProfessionDetail

object VacancyDbMapper {
    fun map(vacancy: ProfessionDetail): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id,
            name = vacancy.name,
            employmentId = vacancy.employment?.id,
            employerId = vacancy.employer?.id,
            address = vacancy.address,
            experienceId = vacancy.experience?.id,
            salaryFrom = vacancy.salary?.from,
            salaryTo = vacancy.salary?.to,
            currency = vacancy.salary?.currency?.name,
            description = vacancy.description,
            keySkills = vacancy.keySkills,
            phone = vacancy.phone,
            contactName = vacancy.contactName,
            email = vacancy.email,
            comment = vacancy.comment,
            url = vacancy.url
        )
    }

    fun map(
        vacancyEntity: VacancyEntity,
        employmentEntity: EmploymentEntity? = null,
        employerEntity: EmployerEntity? = null,
        experienceEntity: ExperienceEntity? = null
    ): ProfessionDetail {
        val employment =
            if (employmentEntity != null) EmploymentDbMapper.map(employmentEntity) else null
        val employer = if (employerEntity != null) EmployerDbMapper.map(employerEntity) else null
        val experience =
            if (experienceEntity != null) ExperienceDbMapper.map(experienceEntity) else null
        val salary = Salary(
            from = vacancyEntity.salaryFrom,
            to = vacancyEntity.salaryTo,
            currency = Currency.getCurrency(vacancyEntity.currency.toString())
        )

        return ProfessionDetail(
            id = vacancyEntity.id,
            name = vacancyEntity.name.toString(),
            employment = employment,
            employer = employer,
            address = vacancyEntity.address,
            experience = experience,
            salary = salary,
            description = vacancyEntity.description,
            keySkills = vacancyEntity.keySkills,
            phone = vacancyEntity.phone,
            contactName = vacancyEntity.contactName,
            email = vacancyEntity.email,
            comment = vacancyEntity.comment,
            url = vacancyEntity.url
        )
    }
}