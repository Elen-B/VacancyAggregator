package ru.practicum.android.diploma.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.core.db.AppDatabase
import ru.practicum.android.diploma.core.network.HhunterApi
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.RetrofitNetworkClient
import ru.practicum.android.diploma.util.BASE_URL

val dataModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        get<Retrofit>().create(HhunterApi::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get(), androidContext())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db").build()
    }
}