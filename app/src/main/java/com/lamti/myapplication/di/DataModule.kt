package com.lamti.myapplication.di

import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.api.RetrofitPokemonNetwork
import com.lamti.myapplication.data.repository.OfflineFirstPokemonRepository
import com.lamti.myapplication.data.repository.PokemonRepository
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