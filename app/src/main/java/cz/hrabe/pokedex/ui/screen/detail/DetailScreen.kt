package cz.hrabe.pokedex.ui.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Composable
fun DetailScreen(
    detailScreenViewModel: DetailScreenViewModel,
    modifier: Modifier = Modifier
) {

}

class DetailScreenViewModelFactory(private val pokemonId: Int) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailScreenViewModel(pokemonId) as T
    }
}