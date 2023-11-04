package ru.practicum.android.diploma.details.data.remote


import com.google.gson.annotations.SerializedName

data class DetailDto(
    @SerializedName("area")
    val area: Area,
    @SerializedName("employer")
    val employer: Employer,
    @SerializedName("employment")
    val employment: Employment,
    @SerializedName("experience")
    val experience: Experience?,
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("salary")
    val salary: Salary?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("key_skills")
    val keySkills: List<KeySkill>?,
    @SerializedName("contacts")
    val contacts: Contacts?
)