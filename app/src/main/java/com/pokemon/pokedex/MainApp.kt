package com.pokemon.pokedex

import android.app.Application
import com.pokemon.pokedex.di.networkModule
import com.pokemon.pokedex.di.repositoryModule
import com.pokemon.pokedex.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApp)
            modules(listOf(networkModule, repositoryModule, viewModelModule))
        }
    }
}