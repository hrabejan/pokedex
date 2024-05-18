package cz.hrabe.pokedex.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import cz.hrabe.pokedex.domain.GetPokemonPagingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(getPokemonPagingDataUseCase: GetPokemonPagingDataUseCase) :
    ViewModel() {

    val pokemon = getPokemonPagingDataUseCase().cachedIn(viewModelScope)
}