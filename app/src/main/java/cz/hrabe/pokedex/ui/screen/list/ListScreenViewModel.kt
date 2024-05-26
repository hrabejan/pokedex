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
) : ViewModel() {

    /**
     * Paginaton flow of Pokemon with colors.
     * Cached in the ViewModel scope, so the pagination is not reloaded on configuration change.
     */
    val pokemon = getPokemonWithColorsPagingDataUseCase().cachedIn(viewModelScope)

    /**
     * Takes in a pokemon for it's ID and a drawable of the image that has been loaded.
     * The drawable will be used to calculate it's average and contrast color.
     * That result will then be saved into a local database with it's respective Pokemon id.
     *
     * @param pokemon Pokemon for which the image has been loaded, used only for it's ID.
     * @param drawable Loaded image of a Pokemon.
     */
    fun onImageLoaded(pokemon: Pokemon, drawable: Drawable) {
        viewModelScope.launch {
            updatePokemonsColorsUserCase(pokemon.id, drawable)
        }
    }
}