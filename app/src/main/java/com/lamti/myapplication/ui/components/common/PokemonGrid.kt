package com.lamti.myapplication.ui.components.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.data.repository.model.Pokemon

@Composable
fun PokemonGrid(
    pokemonList: LazyPagingItems<Pokemon>,
    onPokemonClick: (code: Int, color: Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(pokemonList.itemCount) { index ->
            pokemonList[index]?.let {
                PokemonCard(
                    modifier = Modifier.padding(4.dp),
                    name = it.name,
                    code = it.code.toString(),
                    image = it.image,
                    type1 = it.type1,
                    type2 = it.type2,
                    onClick = { dominantColor ->
                        onPokemonClick(it.code, dominantColor)
                    }
                )
            }
        }
    }
}
