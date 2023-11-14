package ru.practicum.android.diploma.search.presentation

import ru.practicum.android.diploma.filter.domain.models.FilterParameters

object FilterMapper {

    fun getMap(filterParameters: FilterParameters): HashMap<String,String>{
        val resultMap: HashMap<String,String> = hashMapOf()
        val area = filterParameters.region?.id ?: filterParameters.country?.id
        val industry = filterParameters.industry?.id
        val salary = filterParameters.salary
        val fSalaryRequired = filterParameters.fSalaryRequired
        hashMapOf(
            "area" to area,
            "industry" to industry,
            "salary" to salary,
            "only_with_salary" to fSalaryRequired).map {
            if(it.value != null){
                resultMap.put(it.key, it.value.toString())
                }
            }
        return resultMap
    }
}