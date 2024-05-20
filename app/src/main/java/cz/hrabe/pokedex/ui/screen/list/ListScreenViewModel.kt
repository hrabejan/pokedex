package cz.hrabe.pokedex.ui.screen.list

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import cz.hrabe.pokedex.domain.GetPokemonPagingDataUseCase
import cz.hrabe.pokedex.domain.Pokemon
import cz.hrabe.pokedex.domain.UpdatePokemonAverageColorUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    getPokemonPagingDataUseCase: GetPokemonPagingDataUseCase,
    private val updatePokemonAverageColorUserCase: UpdatePokemonAverageColorUserCase
) :
    ViewModel() {

    val pokemon = getPokemonPagingDataUseCase().cachedIn(viewModelScope)

    fun onImageLoaded(pokemon: Pokemon, drawable: Drawable) {
        viewModelScope.launch {
            updatePokemonAverageColorUserCase(pokemon, drawable)
        }
    }
}