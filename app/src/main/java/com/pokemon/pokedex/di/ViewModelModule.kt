package com.pokemon.pokedex.di


import com.pokemon.pokedex.ui.home.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val viewModelModule = module {

    viewModel { MainViewModel(get()) }
}