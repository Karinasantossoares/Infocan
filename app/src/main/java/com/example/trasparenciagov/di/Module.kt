package com.example.trasparenciagov.di

import android.content.Context
import com.example.trasparenciagov.preference.AppPreferences
import com.example.trasparenciagov.repository.networkRepository.InfocanRepository
import com.example.trasparenciagov.retrofit.initRetrofit
//import com.example.trasparenciagov.room.AppDataBase
import com.example.trasparenciagov.service.InfocanService
import com.example.trasparenciagov.useCase.InfocanUseCase
import com.example.trasparenciagov.viewModel.InfocanViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val infocanModule = module {
    single { InfocanRepository(get()) }
    single { get<Retrofit>().create(InfocanService::class.java) }
    single { initRetrofit() }
    single { InfocanUseCase(androidContext(),get()) }
    single { androidContext().getSharedPreferences(AppPreferences.NAME, Context.MODE_PRIVATE) }
    single { AppPreferences(get()) }
//    single { AppDataBase.instance(androidContext()) }
//    single {get<AppDataBase>().perfilPersonDao()  }
//    single {get<AppDataBase>().detailsPersonDao()  }
//    single {get<AppDataBase>().expenseDao()  }
    viewModel { InfocanViewModel(androidContext(),get(),get())}
}


