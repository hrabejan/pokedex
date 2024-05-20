package cz.hrabe.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import cz.hrabe.pokedex.ui.screen.Destination
import cz.hrabe.pokedex.ui.screen.detail.DetailScreen
import cz.hrabe.pokedex.ui.screen.detail.DetailScreenViewModel
import cz.hrabe.pokedex.ui.screen.list.ListScreen
import cz.hrabe.pokedex.ui.screen.list.ListScreenViewModel
import cz.hrabe.pokedex.ui.theme.PokeDexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PokeDexTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Destination.List) {
                    composable<Destination.List> {
                        val listScreenViewModel =
                            ViewModelProvider(this@MainActivity)[ListScreenViewModel::class.java]
                        ListScreen(listScreenViewModel = listScreenViewModel) { pokemon ->
                            navController.navigate(Destination.Detail(pokemon.id))
                        }
                    }
                    composable<Destination.Detail> { backStackEntry ->
                        val detail: Destination.Detail = backStackEntry.toRoute()
                        val detailScreenViewModel =
                            hiltViewModel<DetailScreenViewModel, DetailScreenViewModel.Factory> { factory ->
                                factory.create(detail.id)
                            }
                        DetailScreen(
                            detailScreenViewModel = detailScreenViewModel
                        ) {
                            navController.popBackStack(Destination.List, false)
                        }
                    }
                }
            }
        }
    }
}