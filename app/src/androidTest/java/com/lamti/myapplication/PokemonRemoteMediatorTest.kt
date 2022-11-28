package com.lamti.myapplication

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lamti.myapplication.data.database.PokemonDatabase
import com.lamti.myapplication.data.database.PokemonEntity
import com.lamti.myapplication.data.network.api.PokemonNetworkDataSource
import com.lamti.myapplication.data.paging.PokemonRemoteMediator
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PokemonRemoteMediatorTest {

    private val network: PokemonNetworkDataSource = FakePokemonNetworkDataSource()
    private val mockDb = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        PokemonDatabase::class.java
    ).build()

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        (network as FakePokemonNetworkDataSource).addData()
        val remoteMediator = PokemonRemoteMediator(network = network, database = mockDb)
        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(20),
            leadingPlaceholderCount = 20
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        val remoteMediator = PokemonRemoteMediator(network = network, database = mockDb)
        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(20),
            leadingPlaceholderCount = 20
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Success)
        assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        (network as FakePokemonNetworkDataSource).failure()
        val remoteMediator = PokemonRemoteMediator(network = network, database = mockDb)
        val pagingState = PagingState<Int, PokemonEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(20),
            leadingPlaceholderCount = 20
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(result is RemoteMediator.MediatorResult.Error)
    }
}