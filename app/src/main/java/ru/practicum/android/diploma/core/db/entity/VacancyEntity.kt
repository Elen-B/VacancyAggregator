package ru.practicum.android.diploma.core.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName ="vacancy_table")
data class VacancyEntity(
    @PrimaryKey @ColumnInfo(name = "id")
    val id: String,
    val name: String?,
    val salary: String,
    val employer: String,
    val logo: String?
)