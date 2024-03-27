package com.lamti.pokemonlist.components.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamti.pokemonlist.parseStatToAbbr
import com.lamti.pokemonlist.parseStatToColor

@Composable
fun PokemonBaseStats(
    pokemon: com.lamti.pokemon.model.Pokemon,
    text: String = "Base Stats",
    animationDelayPerItem: Int = 100
) {
    if (pokemon.stats.isEmpty()) return

    val maxBaseStat = remember(pokemon.stats) { pokemon.stats.maxOf { it.baseStat } }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = text,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface
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