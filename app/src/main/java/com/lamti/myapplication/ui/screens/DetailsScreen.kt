package com.lamti.myapplication.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.components.PokemonLoader
import com.lamti.myapplication.ui.components.PokemonTopBar
import com.lamti.myapplication.ui.theme.WhiteTransparent
import com.lamti.myapplication.ui.util.parseStatToAbbr
import com.lamti.myapplication.ui.util.parseStatToColor

@Composable
internal fun DetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel(),
) {
    val uiState: DetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()

    DetailsScreen(
        uiState = uiState,
        modifier = modifier,
        onBackClick = onBackClick,
    )
}

@Composable
fun DetailsScreen(
    uiState: DetailsUiState,
    modifier: Modifier,
    onBackClick: () -> Unit,
) {
    val sheetHeight = (LocalConfiguration.current.screenHeightDp / 2).dp
    val scaffoldState = rememberBottomSheetScaffoldState()

    BackHandler {
        onBackClick()
    }

    when (uiState) {
        DetailsUiState.Error -> Box {}
        is DetailsUiState.Loading -> PokemonLoader(modifier.background(uiState.dominantColor))
        is DetailsUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = sheetHeight,
                    sheetShape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
                    sheetGesturesEnabled = false,
                    sheetContent = { BottomSheetContent(uiState.pokemon) },
                    topBar = {
                        PokemonTopBar(
                            color = uiState.dominantColor,
                            onBackClick = onBackClick
                        )
                    },
                ) {
                    DetailsContent(
                        pokemon = uiState.pokemon,
                        modifier = modifier.background(uiState.dominantColor),
                        height = sheetHeight
                    )
                }
                AsyncImage(
                    model = uiState.pokemon.image,
                    contentDescription = uiState.pokemon.name,
                    modifier = Modifier
                        .size(300.dp)
                        .aspectRatio(1f)
                        .align(Alignment.Center)
                        .offset(y = (-100).dp),
                )
            }
        }
    }
}

@Composable
fun DetailsContent(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    height: Dp,
) {
    PokemonDetails(
        modifier = modifier,
        height = height,
        pokemon = pokemon
    )
}

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
                    if (type2 != null) {
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

@Composable
fun BottomSheetContent(pokemon: Pokemon) = with(pokemon) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(top = 50.dp, start = 20.dp, end = 20.dp)) {
        PokemonBaseStats(pokemon = pokemon)
    }
}

@Composable
fun PokemonBaseStats(
    pokemon: Pokemon,
    animDelayPerItem: Int = 100
) {
    val maxBaseStat = remember {
        pokemon.stats.maxOf { it.baseStat }
    }
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Base stats:",
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
                animDelay = i * animDelayPerItem
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun PokemonStat(
    statName: String,
    statValue: Int,
    statMaxValue: Int,
    statColor: Color,
    height: Dp = 28.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val curPercent = animateFloatAsState(
        targetValue = if (animationPlayed) {
            statValue / statMaxValue.toFloat()
        } else 0f,
        animationSpec = tween(animDuration, animDelay)
    )
    LaunchedEffect(Unit) { animationPlayed = true }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .clip(CircleShape)
            .background(if (isSystemInDarkTheme()) Color.Gray else Color.LightGray)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(curPercent.value)
                .clip(CircleShape)
                .background(statColor)
                .padding(horizontal = 8.dp)
        ) {
            Text(
                text = statName,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = (curPercent.value * statMaxValue).toInt().toString(),
                fontWeight = FontWeight.Bold
            )
        }
    }
}