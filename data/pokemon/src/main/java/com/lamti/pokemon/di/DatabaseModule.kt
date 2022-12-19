package com.lamti.pokemon.di

import android.content.Context
import androidx.room.Room
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
    ): com.lamti.pokemon.database.PokemonDatabase = Room.databaseBuilder(
        /* context = */ context,
        /* klass = */ com.lamti.pokemon.database.PokemonDatabase::class.java,
        /* name = */ "pokemon-database"
    ).build()

    @Provides
    fun providesPokemonDao(database: com.lamti.pokemon.database.PokemonDatabase): com.lamti.pokemon.database.PokemonDao =
        database.pokemonDao()

    @Provides
    fun providesPokemonRemoteKeysDao(database: com.lamti.pokemon.database.PokemonDatabase): com.lamti.pokemon.database.PokemonRemoteKeysDao =
        database.pokemonRemoteKeysDao()
}