package com.lamti.pokemon.di

import com.lamti.pokemon.OfflineFirstPokemonRepository
import com.lamti.pokemon.PokemonRepository
import com.lamti.pokemon.network.api.PokemonNetworkDataSource
import com.lamti.pokemon.network.api.RetrofitPokemonNetwork
import org.koin.dsl.module

val dataSourceModule = module {
    single<PokemonNetworkDataSource> { RetrofitPokemonNetwork(networkApi = get()) }
    single<PokemonRepository> { OfflineFirstPokemonRepository(network = get(), database = get()) }
}
