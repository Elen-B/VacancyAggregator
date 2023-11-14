package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.domain.models.Vacancy
import ru.practicum.android.diploma.core.data.network.dto.VacancyDTO

object VacancyMapper {
    fun map(vacancyDTO: VacancyDTO): Vacancy {
        return Vacancy(
            id = vacancyDTO.id,
            name = vacancyDTO.name,
            area = vacancyDTO.area?.let { AreaMapper.map(it) },
            salary = SalaryMapper.map(vacancyDTO.salary),
            employer = vacancyDTO.employer?.let { EmployerMapper.map(it) }
        )
    }
}