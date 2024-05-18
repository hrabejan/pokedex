package cz.hrabe.pokedex.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.hrabe.pokedex.domain.GetSinglePokemonFromDbUseCase
import cz.hrabe.pokedex.domain.Pokemon
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailScreenViewModel @AssistedInject constructor(
    @Assisted private val id: Int,
    private val getSinglePokemonFromDbUseCase: GetSinglePokemonFromDbUseCase
) : ViewModel() {

    private val _detailUiState = MutableStateFlow(DetailUiState())
    val detailUiState: StateFlow<DetailUiState> = _detailUiState

    init {
        viewModelScope.launch {
            getSinglePokemonFromDbUseCase(id).collect { pokemon ->
                _detailUiState.update {
                    it.copy(pokemon = pokemon)
                }
            }
        }
    }


    @AssistedFactory
    interface DetailScreenViewModelFactory {
        fun create(id: Int): DetailScreenViewModel
    }
}

data class DetailUiState(val pokemon: Pokemon? = null)