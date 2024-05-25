package cz.hrabe.pokedex.ui.screen.list

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import cz.hrabe.pokedex.domain.GetPokemonWithColorsPagingDataUseCase
import cz.hrabe.pokedex.domain.model.Pokemon
import cz.hrabe.pokedex.domain.UpdatePokemonsColorsUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    getPokemonWithColorsPagingDataUseCase: GetPokemonWithColorsPagingDataUseCase,
    private val updatePokemonsColorsUserCase: UpdatePokemonsColorsUserCase,
) :
    ViewModel() {

    val pokemon = getPokemonWithColorsPagingDataUseCase().cachedIn(viewModelScope)

    fun onImageLoaded(pokemon: Pokemon, drawable: Drawable) {
        viewModelScope.launch {
            updatePokemonsColorsUserCase(pokemon.id, drawable)
        }
    }
}