package com.lamti.myapplication.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.lamti.myapplication.ui.Pokemon
import com.lamti.myapplication.ui.components.PokemonFAB
import com.lamti.myapplication.ui.components.PokemonTopBar
import com.lamti.myapplication.ui.theme.Green
import com.lamti.myapplication.ui.theme.WhiteTransparent
import kotlinx.coroutines.launch

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
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BackHandler {
        onBackClick()
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = sheetHeight,
        sheetShape = RoundedCornerShape(24.dp, 24.dp, 0.dp, 0.dp),
        sheetGesturesEnabled = false,
        sheetContent = { BottomSheetContent() },
        topBar = { PokemonTopBar(onBackClick = onBackClick) },
        floatingActionButton = {
            PokemonFAB {
                coroutineScope.launch {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = "This is your message",
                        actionLabel = "Do something"
                    )
                }
            }
        },
    ) {
        DetailsContent(
            modifier,
            sheetHeight,
            uiState
        )
    }
}

@Composable
fun BottomSheetContent() {
    Box(Modifier.fillMaxSize()) {
        Icon(
            imageVector = Icons.Rounded.Call,
            contentDescription = "",
            modifier = Modifier
                .size(130.dp)
                .aspectRatio(1f)
                .align(Alignment.TopCenter)
                .offset(y = (-100).dp),
            tint = MaterialTheme.colors.onBackground,
        )
        Text(text = "hello", modifier = Modifier.clickable {})
    }
}

@Composable
fun DetailsContent(
    modifier: Modifier = Modifier,
    height: Dp,
    uiState: DetailsUiState,
) {
    when (uiState) {
        DetailsUiState.Error -> TODO()
        DetailsUiState.Loading -> CircularProgressIndicator()
        is DetailsUiState.Success -> PokemonDetails(
            modifier = modifier,
            height = height,
            pokemon = uiState.pokemon
        )
    }
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
            .background(Green)
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
                text = "#${code.toUpperCase(Locale.current)}",
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
        Icon(
            imageVector = Icons.Rounded.Call,
            contentDescription = "",
            modifier = Modifier
                .size(130.dp)
                .aspectRatio(1f)
                .align(Alignment.BottomCenter)
                .offset(y = -(24.5).dp),
            tint = MaterialTheme.colors.onBackground,
        )
    }
}