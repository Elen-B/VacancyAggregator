package ru.practicum.android.diploma.favourites.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.core.db.entity.VacancyEntity
import ru.practicum.android.diploma.search.domain.models.CurrencyType
import ru.practicum.android.diploma.search.domain.models.Employer
import ru.practicum.android.diploma.search.domain.models.Salary
import ru.practicum.android.diploma.search.domain.models.SearchVacancy

class VacancyConvertor(private var json: Gson) {
    fun map(vacancy: SearchVacancy): VacancyEntity {
        return VacancyEntity(
            id = vacancy.id.toString(),
            name = vacancy.name,
            salary = json.toJson(vacancy.salary).toString(),
            employer = json.toJson(vacancy.employer).toString(),
            logo = vacancy.logo
        )
    }

    fun map(vacancy: VacancyEntity): SearchVacancy {
        return SearchVacancy(
            id = vacancy.id,
            name = vacancy.name,
            salary = createSalary(vacancy.salary),
            employer = createEmployer(vacancy.employer),
            logo = vacancy.logo
        )
    }

     fun createSalary(jsonSalary: String): Salary {
        var newSalary = Salary(from = "", to = "", currency = CurrencyType.RUR, gross = false)
        if (jsonSalary.isNotEmpty()){
            newSalary =  json.fromJson(jsonSalary, object : TypeToken<Salary>(){}.type)
        }
        return newSalary
    }

     fun createEmployer(jsonEmployer: String): Employer {
        var newEmployer = Employer(id = "", name = "")
        if (jsonEmployer.isNotEmpty()){
            newEmployer = json.fromJson(jsonEmployer, object : TypeToken<Employer>(){}.type)
        }
        return newEmployer
    }
}