package ru.practicum.android.diploma.di

import android.content.Context
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.practicum.android.diploma.core.network.HhunterApi
import ru.practicum.android.diploma.core.network.NetworkClient
import ru.practicum.android.diploma.core.network.RetrofitNetworkClient
import ru.practicum.android.diploma.util.BASE_URL
import ru.practicum.android.diploma.util.SHARED_PREFS

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
        androidContext()
            .getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
    }

    factory {
        Gson()
    }
}