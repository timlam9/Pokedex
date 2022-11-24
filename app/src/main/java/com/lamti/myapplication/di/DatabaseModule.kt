package com.lamti.myapplication.di

import android.content.Context
import androidx.room.Room
import com.lamti.myapplication.data.database.PokemonDao
import com.lamti.myapplication.data.database.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): PokemonDatabase = Room.databaseBuilder(
        /* context = */ context,
        /* klass = */ PokemonDatabase::class.java,
        /* name = */ "pokemon-database"
    ).build()

    @Provides
    fun providesAuthorDao(database: PokemonDatabase): PokemonDao = database.pokemonDao()
}