package com.lamti.myapplication.ui.components.common

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.lamti.myapplication.ui.theme.BlackTransparent
import com.lamti.myapplication.ui.theme.Green
import com.lamti.myapplication.ui.theme.PokedexTheme
import com.lamti.myapplication.ui.theme.WhiteTransparent

@Composable
fun PokemonCard(
    modifier: Modifier = Modifier,
    name: String,
    code: String,
    image: String,
    type1: String,
    type2: String?,
    cornerSize: CornerSize = CornerSize(16),
    height: Dp = 130.dp,
    contentColor: Color = Color.White,
    backgroundColor: Color = Green,
    typeShape: RoundedCornerShape = RoundedCornerShape(80),
    typeBackground: Color = WhiteTransparent,
    onClick: (color: Int) -> Unit,
) {
    var dominantColor by remember { mutableStateOf(backgroundColor) }

    Card(
        shape = MaterialTheme.shapes.medium.copy(cornerSize),
        modifier = modifier
            .height(height)
            .clickable { onClick(dominantColor.toArgb()) },
        contentColor = contentColor,
        backgroundColor = dominantColor
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(start = 20.dp)
        ) {
            Text(
                text = "#${code.toUpperCase(Locale.current).padStart(3, '0')}",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp, end = 16.dp),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    color = BlackTransparent
                ),
            )
            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = name.capitalize(Locale.current),
                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = type1,
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier
                                .clip(typeShape)
                                .background(typeBackground)
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                        if (type2 != null) {
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = type2,
                                style = MaterialTheme.typography.subtitle1,
                                modifier = Modifier
                                    .clip(typeShape)
                                    .background(typeBackground)
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .offset(x = 4.dp, y = 16.dp)
                        .size(90.dp)
                        .aspectRatio(1f)
                        .align(Alignment.BottomEnd)
                        .clip(RoundedCornerShape(90))
                        .background(WhiteTransparent),
                )
                AsyncImage(
                    model = image,
                    contentDescription = name,
                    modifier = Modifier
                        .size(90.dp)
                        .aspectRatio(1f)
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 0.dp, end = 12.dp),
                    onState = {
                        when (it) {
                            AsyncImagePainter.State.Empty -> {}
                            is AsyncImagePainter.State.Error -> {}
                            is AsyncImagePainter.State.Loading -> {}
                            is AsyncImagePainter.State.Success -> {
                                val result = it.result.drawable
                                val bitmap = (result as BitmapDrawable).bitmap.copy(
                                    Bitmap.Config.ARGB_8888,
                                    true
                                )
                                Palette.from(bitmap).generate { palette ->
                                    palette?.dominantSwatch?.rgb?.let { colorValue ->
                                        dominantColor = Color(colorValue)
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PokedexTheme {
        PokemonCard(
            name = "Bulbasar",
            code = "#001",
            image = "",
            type1 = "grass",
            type2 = "poison",
            onClick = {}
        )
    }
}