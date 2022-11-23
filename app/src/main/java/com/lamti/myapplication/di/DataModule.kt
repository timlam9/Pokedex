package com.lamti.myapplication.di

import com.lamti.myapplication.data.FakePokemonRepository
import com.lamti.myapplication.data.PokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsPokemonRepository(
        fakePokemonRepository: FakePokemonRepository
    ): PokemonRepository
}