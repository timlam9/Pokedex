package com.lamti.myapplication.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lamti.myapplication.ui.theme.Green

@Composable
fun PokemonTopBar(
    title: String = "",
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier,
        backgroundColor = Green,
        contentColor = Color.White,
        elevation = 2.dp,
        title = { if (title.isNotEmpty()) Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(Icons.Filled.ArrowBack, "backIcon")
            }
        },
        actions = {
            IconButton(onClick = onFavoriteClick) {
                Icon(Icons.Filled.Favorite, "favoriteIcon")
            }
        }
    )
}