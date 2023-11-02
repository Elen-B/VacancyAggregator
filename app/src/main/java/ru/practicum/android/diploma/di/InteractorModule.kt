package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.details.domain.usecase.DetailsInterActor
import ru.practicum.android.diploma.details.domain.usecase.DetailsInterActorImpl

val interactorModule = module {
    single <DetailsInterActor> { DetailsInterActorImpl(get()) }
}