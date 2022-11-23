package com.lamti.myapplication.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakePokemonRepository @Inject constructor() : PokemonRepository {
    override fun getPokemonStream(code: String): Flow<Pokemon> {
        return flowOf(bulbasar)
    }

    override fun getPokemonListStream(): Flow<List<Pokemon>> {
        return flowOf(pokemons)
    }

    private val bulbasar = Pokemon(
        name = "bulbasar",
        code = "001",
        image = "image",
        type1 = "grass",
        type2 = "poison"
    )

    private val pokemons = listOf(
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
        Pokemon(name = "bulbasar", code = "#001", image = "", type1 = "grass", type2 = "poison"),
    )
}