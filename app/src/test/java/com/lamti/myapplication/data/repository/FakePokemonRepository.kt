package com.lamti.myapplication.data.repository

import androidx.paging.PagingData
import com.lamti.myapplication.data.repository.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class FakePokemonRepository @Inject constructor() : PokemonRepository {

    override fun getPokemonStream(code: Int): Flow<Pokemon> {
        return flowOf(bulbasaur)
    }

    override fun getPokemonListStream(): Flow<PagingData<Pokemon>> {
        return flowOf(pagingData)
    }

    private val bulbasaur = Pokemon(
        name = "bulbasaur",
        code = 1,
        image = "image",
        type1 = "grass",
        type2 = "poison",
        stats = emptyList()
    )

    private val pokemons = List(20) {
        Pokemon(
            name = "bulbasaur$it",
            code = it,
            image = "",
            type1 = "grass",
            type2 = "poison",
            stats = listOf()
        )
    }

    private val pagingData: PagingData<Pokemon> = PagingData.from(pokemons)
}