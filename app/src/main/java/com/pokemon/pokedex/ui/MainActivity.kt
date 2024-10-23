package com.pokemon.pokedex.ui

import android.media.MediaDrm.LogMessage
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.test.core.app.ActivityScenario.launch
import com.pokemon.pokedex.ui.home.HomeScreen
import com.pokemon.pokedex.ui.home.MainViewModel
import com.pokemon.pokedex.ui.theme.PokedexTheme
import kotlinx.coroutines.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      val scope1=  lifecycleScope.launch {
//            //
//            val scope = CoroutineScope(Dispatchers.Main).launch {
//
//            }
//
//
//            val scopetest = coroutineScope {
//                try {
//                    val usersDeferred = try {
//                        async {
//                            getUsers()
//                        }
//                    } catch (e: Exception) {
//                        Log.e("error1", e.toString())
//                    }
//                } catch (e: Exception) {
//                    Log.e("parent blcok", e.toString())
//                }
//            }
//        }

        setContent {
            PokedexTheme {
                HomeScreen(mainViewModel)
            }
        }
    }

    private fun getMoreUsers() {
        Log.d("MainAct", "getMoreUsers() called")
        throw Exception("getMore Exception")
    }

    private fun getUsers() {
//        try {
        Log.d("MainAct", "getUsers() called")
        throw Exception("getUser Exception")
//        }catch (e:Exception){
//            Log.d("errorfinal", "getUsers() called")
//        }
    }
}




