package com.lamti.myapplication.ui.components.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.components.common.LoadingItem

@Composable
fun PokemonGrid(
    pokemonList: LazyPagingItems<Pokemon>,
    modifier: Modifier = Modifier,
    title: String = "Pokedex",
    onPokemonClick: (code: Int, color: Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp)
    ) {
        title(title)
        items(pokemonList.itemCount) { index ->
            pokemonList[index]?.let {
                PokemonCard(
                    modifier = Modifier.padding(4.dp),
                    name = it.name,
                    code = it.code,
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

private fun LazyGridScope.title(title: String) {
    item("title_start") {
        Text(
            text = title, modifier = Modifier
                .height(70.dp)
                .padding(top = 10.dp, start = 10.dp),
            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.primary)
        )
    }
    item("title_end") {}
}

private fun LazyGridScope.loadingItem(pokemonList: LazyPagingItems<Pokemon>) {
    if (pokemonList.loadState.append == LoadState.Loading) {
        item(key = "append_loading_start") { LoadingItem() }
        item(key = "append_loading_end") { LoadingItem() }
    }
}