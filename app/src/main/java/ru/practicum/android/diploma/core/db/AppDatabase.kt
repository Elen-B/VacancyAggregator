package ru.practicum.android.diploma.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.practicum.android.diploma.core.db.entity.VacancyEntity

@Database(
    version = 1,
    entities = [VacancyEntity::class]
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun vacancyDao(): VacancyDao
}