package cz.hrabe.pokedex.ui.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.hrabe.pokedex.R
import cz.hrabe.pokedex.domain.Pokemon

@Composable
fun ListScreen(listScreenViewModel: ListScreenViewModel, modifier: Modifier = Modifier, onClick: (Pokemon) -> Unit) {
    val pokemonPagingItems = listScreenViewModel.pokemon.collectAsLazyPagingItems()
    val perRow = 2

    Scaffold(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (pokemonPagingItems.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(perRow)) {
                    items(
                        count = pokemonPagingItems.itemCount,
                        key = pokemonPagingItems.itemKey { it.id }) { index ->
                        val pokemon = pokemonPagingItems[index]
                        pokemon?.let {
                            PokemonItem(
                                pokemon = it,
                                modifier = Modifier
                                    .fillMaxWidth(1f / perRow)
                                    .height(200.dp), onClick = onClick
                            )
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun PokemonItem(pokemon: Pokemon, modifier: Modifier = Modifier, onClick: (Pokemon) -> Unit) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(), modifier = modifier
            .padding(8.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .clickable { onClick(pokemon) }
        ) {
            val (number, col, image, backgroundImg, name) = createRefs()

            NumberHeader(number = pokemon.id,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 16.dp)
                    .constrainAs(number) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    }.zIndex(2f))

            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                ),
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(number.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(start = 16.dp, bottom = 16.dp)
                    .zIndex(2f)
            )

            TypeList(pokemon.types, modifier = Modifier
                .constrainAs(col) {
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                }
                .padding(start = 16.dp, bottom = 16.dp)
                .zIndex(2f))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Image of ${pokemon.name}",
//                placeholder = R.drawable.pokemonImagePlaceholder,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(name.bottom)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                        start.linkTo(col.end)
                        height = Dimension.fillToConstraints
                    }
                    .zIndex(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.pokeball),
                contentDescription = "Background pokeball",
                colorFilter = ColorFilter.tint(Color.White.copy(alpha = 0.1f)),
                modifier = Modifier.constrainAs(backgroundImg) {
                    top.linkTo(name.bottom)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                    height = Dimension.fillToConstraints
                }, contentScale = ContentScale.FillHeight
            )
        }
    }
}

@Composable
fun NumberHeader(number: Int, modifier: Modifier) {
    val numberText = number.toString().padStart(3, '0')
    Text(
        text = "#$numberText",
        textAlign = TextAlign.End,
        style = TextStyle(
            color = Color.Black.copy(alpha = 0.2f),
            fontWeight = FontWeight.ExtraBold,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize
        ),
        modifier = modifier
    )
}

@Composable
fun TypeList(types: List<String>, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(8.dp))
        for (type in types) {
            Type(name = type.replaceFirstChar { it.uppercase() })
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun Type(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.White.copy(alpha = 0.2f), shape = CircleShape, modifier = modifier) {
        Text(
            text = name,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}