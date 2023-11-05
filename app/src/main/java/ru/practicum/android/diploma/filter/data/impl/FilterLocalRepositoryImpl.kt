package ru.practicum.android.diploma.filter.data.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.filter.domain.api.FilterLocalRepository
import ru.practicum.android.diploma.filter.domain.models.FilterParameters
import java.lang.reflect.Type

class FilterLocalRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : FilterLocalRepository {
    override fun getFilterParameters(): FilterParameters? {
        return getFilterParametersFromJson()
    }

    override fun saveFilterParameters(filterParameters: FilterParameters) {
        sharedPreferences.edit()
            .putString(FILTER_PARAMETERS, createJsonFromFilterParameters(filterParameters))
            .apply()
    }

    override fun removeFilterParameters() {
        sharedPreferences.edit {
            remove(FILTER_PARAMETERS)
        }
    }

    private fun createFilterParametersFromJson(json: String): FilterParameters {
        val typeOfTrackList: Type = object : TypeToken<FilterParameters?>() {}.type
        return gson.fromJson(json, typeOfTrackList)
    }

    private fun createJsonFromFilterParameters(filterParameters: FilterParameters): String {
        return gson.toJson(filterParameters)
    }

    private fun getFilterParametersFromJson(): FilterParameters? {
        val json: String? = sharedPreferences.getString(FILTER_PARAMETERS, null)
        return if (json.isNullOrEmpty()) null else createFilterParametersFromJson(json)
    }

    companion object {
        private const val FILTER_PARAMETERS = "filter_parameters"
    }
}