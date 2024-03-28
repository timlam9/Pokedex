package com.lamti.myapplication.di

import com.lamti.pokemonlist.screens.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module


val appModule = module {
    viewModelOf(::HomeViewModel)
}