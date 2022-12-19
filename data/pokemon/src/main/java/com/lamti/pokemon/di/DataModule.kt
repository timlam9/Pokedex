package com.lamti.pokemon.di

import com.lamti.pokemon.OfflineFirstPokemonRepository
import com.lamti.pokemon.PokemonRepository
import com.lamti.pokemon.network.api.PokemonNetworkDataSource
import com.lamti.pokemon.network.api.RetrofitPokemonNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsOfflineFirstPokemonRepository(
        offlineFirstPokemonRepository: OfflineFirstPokemonRepository
    ): PokemonRepository

    @Binds
    fun bindsRetrofitPokemonNetwork(
        retrofitPokemonNetwork: RetrofitPokemonNetwork
    ): PokemonNetworkDataSource

    companion object {
        @Provides
        @Singleton
        fun providesNetworkJson(): Json = Json {
            ignoreUnknownKeys = true
        }
    }
}