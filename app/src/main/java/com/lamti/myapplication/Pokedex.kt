package com.lamti.myapplication

import android.app.Application
import com.lamti.myapplication.di.appModule
import com.lamti.pokemon.di.dataBaseModule
import com.lamti.pokemon.di.dataSourceModule
import com.lamti.pokemon.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

internal class Pokedex: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidLogger()
            androidContext(this@Pokedex)
            modules(
                appModule,
                dataBaseModule,
                dataSourceModule,
                networkModule,
            )
        }
    }
}