package com.lamti.myapplication.ui.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.google.accompanist.pager.rememberPagerState
import com.lamti.myapplication.data.repository.model.Pokemon
import kotlin.math.absoluteValue

@Composable
fun PokemonPager(
    modifier: Modifier = Modifier,
    pokemons: LazyPagingItems<Pokemon>,
    startIndex: Int,
    onPageChange: (Int) -> Unit,
) {
    val pagerState: PagerState = rememberPagerState(startIndex)

    LaunchedEffect(key1 = startIndex) {
        pagerState.scrollToPage(startIndex)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onPageChange(page)
        }
    }

    HorizontalPager(
        count = Int.MAX_VALUE,
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 85.dp),
        modifier = modifier.fillMaxWidth()
    ) { index ->
        val page = minOf(index)

        var tintColor: ColorFilter? by remember {
            mutableStateOf(
                ColorFilter.tint(Color.Black)
            )
        }

        Box(
            Modifier
                .graphicsLayer {
                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                    lerp(
                        start = 0.7f,
                        stop = 1.2f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1.2f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }

                    tintColor = when {
                        pageOffset.coerceIn(0f, 1f) in 0.5f..1f -> ColorFilter.tint(Color.Black)
                        else -> null
                    }
                }
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(Color.Transparent),
        ) {
            AsyncImage(
                model = pokemons[page]?.image,
                contentDescription = pokemons[page]?.name,
                modifier = Modifier.fillMaxSize(),
                colorFilter = tintColor,
            )
        }
    }
}