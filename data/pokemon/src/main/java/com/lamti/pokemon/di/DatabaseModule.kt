package com.lamti.pokemon.di

import android.app.Application
import androidx.room.Room
import com.lamti.pokemon.database.PokemonDao
import com.lamti.pokemon.database.PokemonDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataBaseModule = module {
    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}

private fun provideDataBase(application: Application): PokemonDatabase =
    Room.databaseBuilder(
        /* context = */ application,
        /* klass = */ PokemonDatabase::class.java,
        /* name = */ "pokemon-database"
    ).build()

private fun provideDao(database: PokemonDatabase): PokemonDao = database.pokemonDao()