package com.lamti.myapplication

import android.accounts.NetworkErrorException
import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.network.model.list.NetworkPokemonList
import com.lamti.myapplication.data.network.model.list.Result
import com.lamti.myapplication.data.network.model.pokemon.NetworkPokemon
import com.lamti.myapplication.data.network.model.pokemon.Sprites
import com.lamti.myapplication.data.network.model.pokemon.Type
import com.lamti.myapplication.data.network.model.pokemon.TypeX

class FakePokemonNetworkDataSource : PokemonNetworkDataSource {

    private var results: List<Result> = emptyList()
    private var error: Boolean = false

    override suspend fun getPokemonList(page: Int, pageSize: Int): NetworkPokemonList {
        if (error) throw NetworkErrorException()
        return NetworkPokemonList(
            count = 20,
            next = null,
            previous = null,
            results = results
        )
    }

    override suspend fun getPokemon(code: Int): NetworkPokemon {
        return fakeBulbasaur(code)
    }

    fun addData() {
        results = List(40) {
            Result(name = "bulbasaur$it", url = "https://pokeapi.co/api/v2/pokemon/$it/")
        }
    }

    fun failure() {
        error = true
    }

    private fun fakeBulbasaur(code: Int) = NetworkPokemon(
        name = "bulbasaur$code",
        stats = emptyList(),
        id = code,
        sprites = Sprites(
            backDefault = null,
            backFemale = null,
            backShiny = null,
            backShinyFemale = null,
            frontDefault = null,
            frontFemale = null,
            frontShiny = null,
            frontShinyFemale = null,
            other = null,
            versions = null,
        ),
        types = listOf(
            Type(
                slot = 1,
                type = TypeX(
                    name = "grass",
                    url = "https://pokeapi.co/api/v2/type/12/",
                )
            ),
            Type(
                slot = 2,
                type = TypeX(
                    name = "poison",
                    url = "https://pokeapi.co/api/v2/type/4/",
                )
            ),
        )
    )
}