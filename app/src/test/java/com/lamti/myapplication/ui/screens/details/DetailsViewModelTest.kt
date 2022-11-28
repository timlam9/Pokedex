package com.lamti.myapplication.ui.screens.details

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import com.lamti.myapplication.data.repository.FakePokemonRepository
import com.lamti.myapplication.data.repository.model.Pokemon
import com.lamti.myapplication.ui.navigation.codeArg
import com.lamti.myapplication.ui.navigation.colorArg
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertIs

class DetailsViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val repository = FakePokemonRepository()
    private lateinit var viewModel: DetailsViewModel

    @Before
    fun setup() {
        viewModel = DetailsViewModel(
            savedStateHandle = SavedStateHandle(
                mapOf(
                    codeArg to "1",
                    colorArg to "121212"
                )
            ),
            pokemonRepository = repository,
        )
    }

    @Test
    fun `on success ui state returns the pokemon`() = runTest {
        val collectJob = launch(UnconfinedTestDispatcher()) { viewModel.uiState.collect() }
        val uisState = viewModel.uiState.value

        val result: Pokemon = repository.getPokemonStream(1).first()

        assertIs<DetailsUiState.Success>(uisState)
        assertEquals(result, uisState.pokemon)
        collectJob.cancel()
    }

    @Test
    fun `on initializing ui state returns loading`() = runTest {
        assertEquals(DetailsUiState.Loading(Color(121212)), viewModel.uiState.value)
    }
}