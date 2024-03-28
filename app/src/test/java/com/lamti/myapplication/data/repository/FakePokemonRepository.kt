package com.lamti.myapplication.data.repository

import androidx.paging.PagingData
import com.lamti.pokemon.model.PokemonColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakePokemonRepository() : com.lamti.pokemon.PokemonRepository {

    override fun getPokemonStream(code: Int): Flow<com.lamti.pokemon.model.Pokemon> {
        return flowOf(bulbasaur)
    }

    override fun getPokemonListStream(): Flow<PagingData<com.lamti.pokemon.model.Pokemon>> {
        return flowOf(pagingData)
    }

    private val bulbasaur = com.lamti.pokemon.model.Pokemon(
        name = "bulbasaur",
        code = 1,
        image = "image",
        type1 = "grass",
        type2 = "poison",
        stats = emptyList(),
        color = PokemonColor.Dark
    )

    private val pokemons = List(20) {
        com.lamti.pokemon.model.Pokemon(
            name = "bulbasaur$it",
            code = it,
            image = "",
            type1 = "grass",
            type2 = "poison",
            stats = listOf(),
            color = PokemonColor.Dark
        )
    }

    private val pagingData: PagingData<com.lamti.pokemon.model.Pokemon> = PagingData.from(pokemons)
}