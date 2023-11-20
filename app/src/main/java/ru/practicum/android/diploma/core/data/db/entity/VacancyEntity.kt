package ru.practicum.android.diploma.core.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="vacancy_table")
data class VacancyEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "employment_id")
    val employmentId: String?,
    @ColumnInfo(name = "employer_id")
    val employerId: String?,
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "experience_id")
    val experienceId: String?,
    @ColumnInfo(name = "salary_from")
    val salaryFrom: Int?,
    @ColumnInfo(name = "salary_to")
    val salaryTo: Int?,
    @ColumnInfo(name = "currency")
    val currency: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "key_skills")
    val keySkills: String?,
    @ColumnInfo(name = "phone")
    val phone: String?,
    @ColumnInfo(name = "contact_name")
    val contactName: String?,
    @ColumnInfo(name = "email")
    val email: String?,
    @ColumnInfo(name = "comment")
    val comment: String?,
    @ColumnInfo(name = "url")
    val url: String?,
)