package com.lamti.myapplication.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.data.repository.model.Pokemon

@Composable
fun PokemonGrid(
    pokemonList: LazyPagingItems<Pokemon>,
    onPokemonClick: (code: Int, color: Int) -> Unit
) {
    val state = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = state,
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
        loadingItem(pokemonList)
    }
}

private fun LazyGridScope.loadingItem(pokemonList: LazyPagingItems<Pokemon>) {
    if (pokemonList.loadState.append == LoadState.Loading) {
        item(key = "append_loading_1") { LoadingItem() }
        item(key = "append_loading_2") { LoadingItem() }
    }
}

@Composable
fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.size(160.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

