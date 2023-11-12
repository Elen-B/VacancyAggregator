package ru.practicum.android.diploma.core.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.practicum.android.diploma.core.db.entity.EmployerEntity
import ru.practicum.android.diploma.core.db.entity.EmploymentEntity
import ru.practicum.android.diploma.core.db.entity.ExperienceEntity
import ru.practicum.android.diploma.core.db.entity.VacancyEntity

@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVacancy(vacancy: VacancyEntity)

    @Delete
    suspend fun deleteVacancy(vacancy: VacancyEntity)

    @Query("SELECT * FROM vacancy_table")
    suspend fun getListVacancy(): List<VacancyEntity>

    @Query("SELECT * FROM vacancy_table WHERE id = :id")
    suspend fun getCurrentVacancy(id: String): VacancyEntity

    @Query("SELECT id FROM vacancy_table")
    suspend fun getListId(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployment(employment: EmploymentEntity)

    @Query("SELECT * FROM employment WHERE id = :id")
    suspend fun getEmployment(id: String): EmploymentEntity

    @Delete
    suspend fun deleteEmployment(employment: EmploymentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployer(employer: EmployerEntity)

    @Query("SELECT * FROM employer WHERE id = :id")
    suspend fun getEmployer(id: String): EmployerEntity

    @Delete
    suspend fun deleteEmployer(employer: EmployerEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExperience(experience: ExperienceEntity)

    @Query("SELECT * FROM experience WHERE id = :id")
    suspend fun getExperience(id: String): ExperienceEntity

    @Delete
    suspend fun deleteExperience(experience: ExperienceEntity)
}