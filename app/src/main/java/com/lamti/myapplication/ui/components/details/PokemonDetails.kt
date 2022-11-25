package com.lamti.myapplication.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.theme.WhiteTransparent

@Composable
fun PokemonDetails(
    modifier: Modifier = Modifier,
    height: Dp,
    pokemon: Pokemon,
    typeShape: RoundedCornerShape = RoundedCornerShape(80),
    typeBackground: Color = WhiteTransparent,
) = with(pokemon) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = name.capitalize(Locale.current),
                    style = MaterialTheme.typography.h4.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row {
                    Text(
                        text = type1,
                        style = MaterialTheme.typography.body1.copy(
                            fontSize = 14.sp,
                            color = Color.White
                        ),
                        modifier = Modifier
                            .clip(typeShape)
                            .background(typeBackground)
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                    type2?.let {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = type2,
                            style = MaterialTheme.typography.body1.copy(
                                fontSize = 14.sp,
                                color = Color.White
                            ),
                            modifier = Modifier
                                .clip(typeShape)
                                .background(typeBackground)
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            Text(
                text = "#${code.toString().toUpperCase(Locale.current).padStart(3, '0')}",
                modifier = Modifier.padding(top = 8.dp, end = 16.dp),
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),
            )
        }
        Box(
            modifier = Modifier
                .offset(x = 95.dp, y = -(30).dp)
                .size(200.dp)
                .aspectRatio(1f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(90))
                .background(WhiteTransparent),
        )
    }
}