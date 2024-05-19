package cz.hrabe.pokedex.ui.screen.detail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cz.hrabe.pokedex.domain.Pokemon
import cz.hrabe.pokedex.ui.screen.components.TypeList

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailScreenViewModel: DetailScreenViewModel
) {

    val uiState by detailScreenViewModel.detailUiState.collectAsState()

    Scaffold {
        if (uiState.pokemon == null) {
            Box(modifier = modifier.fillMaxSize().padding(it)) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            PokemonDetail(pokemon = uiState.pokemon!!, modifier = modifier.padding(it))
        }
    }
}

@Composable
fun PokemonDetail(modifier: Modifier = Modifier, pokemon: Pokemon) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(text = pokemon.name)
        TypeList(
            types = pokemon.types,
            orientation = Orientation.Horizontal,
            modifier = Modifier.fillMaxWidth()
        )
    }
}