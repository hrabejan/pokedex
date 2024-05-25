package cz.hrabe.pokedex.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.hrabe.pokedex.domain.GetSinglePokemonWithColorsFromDbUseCase
import cz.hrabe.pokedex.domain.model.PokemonWithColors
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * @param id Pokemon's identifier
 */
@HiltViewModel(assistedFactory = DetailScreenViewModel.Factory::class)
class DetailScreenViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val getSinglePokemonWithColorsFromDbUseCase: GetSinglePokemonWithColorsFromDbUseCase,
) : ViewModel() {

    /**
     * StateFlow holding the current mutable UI State
     */
    private val _detailUiState = MutableStateFlow(DetailUiState())

    /**
     * Immutable getter for UI State
     */
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        //On ViewModels initialization collect the Pokemon with colors via id provided.
        viewModelScope.launch {
            launch {
                getSinglePokemonWithColorsFromDbUseCase(id).collectLatest { pokemon ->
                    //Update ui state on collection
                    _detailUiState.update {
                        it.copy(pokemon = pokemon)
                    }
                }
            }
        }
    }


    /**
     * Used to enable [DetailScreenViewModel] to get a user selected Pokemon's id as a init parameter aside from Hilt's DI
     */
    @AssistedFactory
    interface Factory {
        fun create(id: Int): DetailScreenViewModel
    }
}

/**
 * Container class for UI state related objects
 */
data class DetailUiState(val pokemon: PokemonWithColors? = null)