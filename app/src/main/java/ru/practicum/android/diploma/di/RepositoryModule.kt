package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.data.DetailRepositoryImpl
import ru.practicum.android.diploma.details.domain.impl.DetailRepository

val repositoryModule = module {
    single <DetailRepository> { DetailRepositoryImpl(get()) }
}