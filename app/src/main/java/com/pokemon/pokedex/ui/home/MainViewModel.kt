package com.pokemon.pokedex.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pokemon.pokedex.data.model.*
import com.pokemon.pokedex.data.network.ApiStatus
import com.pokemon.pokedex.data.repository.PokemonRepository
import com.pokemon.pokedex.data.repository.PokemonListSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {
    /**
     * poke description livedata
     */
    private var _pokeDesData = MutableLiveData<PokemonDescriptionData>()
    val pokeDescData: LiveData<PokemonDescriptionData> = _pokeDesData

    /**
     * poke power live data
     */
    private var _pokePowerData = MutableLiveData<PokemonPower>()
    val pokePowerData: LiveData<PokemonPower> = _pokePowerData

    /**
     * poke gender master live data
     */
    private var _pokeGenderData = MutableLiveData<PokeGenderMasterData>()
    val pokeGenderData: LiveData<PokeGenderMasterData> = _pokeGenderData

    /**
     * poke error livedata
     */
    private var _pokeError = MutableLiveData<Boolean>()
    val pokeError: LiveData<Boolean> = _pokeError

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getGenderList()
        }
    }

    /**
     * get gender list master data with detail
     */
    suspend fun getGenderList() {
        pokemonRepository.getPokemonGender().collect {
            if (it.status == ApiStatus.SUCCESS) {
                _pokeGenderData.postValue(it.data!!)
            } else if (it.status == ApiStatus.ERROR) {
                setPokeError()
            }
        }

    }

    /**
     * poke power list api call
     */
    suspend fun getPokePowerList(id: Int?) {
        id?.let {
            pokemonRepository.getPokemonPower("$id").collect {
                if (it.status == ApiStatus.SUCCESS) {
                    _pokePowerData.postValue(it.data!!)
                } else if (it.status == ApiStatus.ERROR) {
                    setPokeError()
                }
            }
        }
    }

    /**
     * poke description data api call
     */
    suspend fun getPokeDescriptionList(id: Int?) {
        id?.let {
            pokemonRepository.getPokemonDescription("$id").collect {
                if (it.status == ApiStatus.SUCCESS) {
                    _pokeDesData.postValue(it.data!!)
                } else if (it.status == ApiStatus.ERROR) {
                    setPokeError()
                }
            }
        }
    }

    private fun setPokeError() {
        _pokeError.value = true
    }

    /**
     * pokemon list api call (internally calling pokemon detail api)
     */
    val pokemonList: Flow<PagingData<ResultsItem>> =
        Pager(PagingConfig(pageSize = 1, initialLoadSize = 2)) {
            PokemonListSource(pokemonRepository)
        }.flow


}