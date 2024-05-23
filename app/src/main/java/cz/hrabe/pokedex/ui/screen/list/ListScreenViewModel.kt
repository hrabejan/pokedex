package cz.hrabe.pokedex.ui.screen.list

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import cz.hrabe.pokedex.data.local.PokemonColorEntity
import cz.hrabe.pokedex.data.local.toPokemonColors
import cz.hrabe.pokedex.domain.GetPokemonPagingDataUseCase
import cz.hrabe.pokedex.domain.GetPokemonsColorsUseCase
import cz.hrabe.pokedex.domain.Pokemon
import cz.hrabe.pokedex.domain.PokemonColors
import cz.hrabe.pokedex.domain.UpdatePokemonsColorsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    getPokemonPagingDataUseCase: GetPokemonPagingDataUseCase,
    private val updatePokemonsColorsUserCase: UpdatePokemonsColorsUserCase,
    private val getPokemonsColorsUseCase: GetPokemonsColorsUseCase
) :
    ViewModel() {

    val pokemon = getPokemonPagingDataUseCase().cachedIn(viewModelScope)

    fun onImageLoaded(pokemon: Pokemon, drawable: Drawable) {
        viewModelScope.launch {
            updatePokemonsColorsUserCase(pokemon, drawable)
        }
    }
}