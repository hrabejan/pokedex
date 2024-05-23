package cz.hrabe.pokedex.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.hrabe.pokedex.domain.GetPokemonsColorsUseCase
import cz.hrabe.pokedex.domain.GetSinglePokemonFromDbUseCase
import cz.hrabe.pokedex.domain.model.Pokemon
import cz.hrabe.pokedex.domain.model.PokemonColors
import cz.hrabe.pokedex.domain.model.PokemonWithColors
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailScreenViewModel.Factory::class)
class DetailScreenViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val getSinglePokemonFromDbUseCase: GetSinglePokemonFromDbUseCase,
) : ViewModel() {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        //On ViewModels initialization collect the Pokemon via id provided.
        viewModelScope.launch {
            launch {
                getSinglePokemonFromDbUseCase(id).collectLatest { pokemon ->
                    _detailUiState.update {
                        it.copy(pokemon = pokemon)
                    }
                }
            }
        }
    }


    @AssistedFactory
    interface Factory {
        fun create(id: Int): DetailScreenViewModel
    }
}

data class DetailUiState(val pokemon: PokemonWithColors? = null)