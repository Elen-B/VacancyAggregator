package ru.practicum.android.diploma.core.data.mapper

import ru.practicum.android.diploma.core.domain.models.Currency
import ru.practicum.android.diploma.core.domain.models.Salary
import ru.practicum.android.diploma.core.network.dto.SalaryDTO

object SalaryMapper {
    fun map(salaryDTO: SalaryDTO?): Salary {
        return Salary(
                from = salaryDTO?.from?.toIntOrNull(),
                to = salaryDTO?.to?.toIntOrNull(),
                currency = Currency.getCurrency(salaryDTO?.currency.toString()),
                gross = salaryDTO?.gross ?: false
            )

    }
}