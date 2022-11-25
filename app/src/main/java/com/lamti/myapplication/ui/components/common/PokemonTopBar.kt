package com.lamti.myapplication.ui.components.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PokemonTopBar(
    title: String = "",
    color: Color,
    onBackClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier,
        backgroundColor = color,
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