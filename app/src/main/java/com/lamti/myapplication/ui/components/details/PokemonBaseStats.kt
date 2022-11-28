package com.lamti.myapplication.ui.components.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.util.parseStatToAbbr
import com.lamti.myapplication.ui.util.parseStatToColor

@Composable
fun PokemonBaseStats(
    pokemon: Pokemon,
    text: String = "Base Stats",
    animationDelayPerItem: Int = 100
) {
    val maxBaseStat = remember { pokemon.stats.maxOf { it.baseStat } }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))

        for (i in pokemon.stats.indices) {
            val stat = pokemon.stats[i]
            PokemonStat(
                statName = parseStatToAbbr(stat),
                statValue = stat.baseStat,
                statMaxValue = maxBaseStat,
                statColor = parseStatToColor(stat),
                animationDelay = i * animationDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}