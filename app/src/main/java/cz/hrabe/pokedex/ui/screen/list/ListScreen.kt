package cz.hrabe.pokedex.ui.screen.list

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import cz.hrabe.pokedex.R
import cz.hrabe.pokedex.domain.model.Pokemon
import cz.hrabe.pokedex.domain.model.PokemonColors
import cz.hrabe.pokedex.ui.screen.components.NumberHeader
import cz.hrabe.pokedex.ui.screen.components.TypeList
import cz.hrabe.pokedex.ui.theme.spacing

/**
 * Screen that lists Pokemon that were locally cached or fetched via API
 * @param onClick Lambda invoked when a Pokemon item in the listed was clicked on.
 *
 * The Pokemon is provided as a parameter.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    listScreenViewModel: ListScreenViewModel,
    modifier: Modifier = Modifier,
    onClick: (Pokemon) -> Unit
) {
    val pokemonPagingItems = listScreenViewModel.pokemon.collectAsLazyPagingItems()

    //Items per one row
    val perRow = 2

    val snackBarHostState = remember { SnackbarHostState() }

    /*
        When loadState changes, see if it is an error.
        If so, display a snackbar message
     */
    LaunchedEffect(key1 = pokemonPagingItems.loadState) {
        if (pokemonPagingItems.loadState.refresh is LoadState.Error) {
            snackBarHostState.showSnackbar(message = "Error: ${(pokemonPagingItems.loadState.refresh as LoadState.Error).error.message}")
        }
    }

    Scaffold(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .drawPokeballBehind(getSize = { hR, wR ->
            val width = size.width / 2f
            Size(width, width * hR)
        }, getOffset = { drawSize ->
            Offset(size.width - drawSize.width, 0f)
        }, color = MaterialTheme.colorScheme.onBackground),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold
                )
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent))
        }, snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (pokemonPagingItems.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.padding(
                        start = MaterialTheme.spacing.large,
                        end = MaterialTheme.spacing.large,
                        top = MaterialTheme.spacing.large
                    ),
                    columns = GridCells.Fixed(perRow)
                ) {
                    items(
                        count = pokemonPagingItems.itemCount,
                        key = pokemonPagingItems.itemKey { it.pokemon.id }) { index ->

                        val pokemonWithColors = pokemonPagingItems[index]

                        pokemonWithColors?.let { pokemonWColors ->
                            PokemonItem(
                                pokemon = pokemonWColors.pokemon,
                                modifier = Modifier
                                    .fillMaxWidth(1f / perRow)
                                    .wrapContentHeight(), onClick = onClick,
                                onImageLoaded = {
                                    listScreenViewModel.onImageLoaded(pokemonWColors.pokemon, it)
                                },
                                pokemonColors = pokemonWColors.pokemonColors ?: PokemonColors(
                                    MaterialTheme.colorScheme.surface,
                                    MaterialTheme.colorScheme.onSurface
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun PokemonItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    onImageLoaded: (Drawable) -> Unit = {},
    pokemonColors: PokemonColors,
    onClick: (Pokemon) -> Unit
) {
    PokemonItemCard(
        modifier = modifier,
        pokemon = pokemon,
        color = pokemonColors.averageColor,
        onClick = onClick
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.mediumLarge)
        ) {
            val (number, col, name, image) = createRefs()

            NumberHeader(number = pokemon.id,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(number) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        width = Dimension.fillToConstraints
                    }
                    .zIndex(2f))

            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                style = TextStyle(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = pokemonColors.contrastColor
                ),
                modifier = Modifier
                    .constrainAs(name) {
                        top.linkTo(number.bottom)
                        start.linkTo(parent.start)
                    }
                    .padding(bottom = MaterialTheme.spacing.medium)
                    .zIndex(2f)
            )

            TypeList(types = pokemon.types, modifier = Modifier
                .constrainAs(col) {
                    top.linkTo(name.bottom)
                    start.linkTo(parent.start)
                }
                .zIndex(2f),
                textStyle = TextStyle(
                    color = pokemonColors.contrastColor,
                    fontSize = 8.sp
                ))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(pokemon.imageUrl)
                    .crossfade(true)
                    .allowHardware(false)
//                        .memoryCacheKey("${pokemon.id}")
                    .build(),
                contentDescription = "Image of ${pokemon.name}",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(name.bottom)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.value(80.dp)
                },
                onSuccess = {
                    onImageLoaded(it.result.drawable)
                }
            )
        }
    }
}


@Composable
fun PokemonItemCard(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onClick: (Pokemon) -> Unit,
    color: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Card(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
        modifier = modifier
            .padding(MaterialTheme.spacing.mediumSmall)
    ) {
        Surface(
            modifier = Modifier
                .clip(CardDefaults.shape)
                .clickable { onClick(pokemon) }, color = color
        ) {


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .drawPokeballBehind(getSize = { hR, _ ->
                        val width = size.width / 1.8f
                        Size(width, width * hR)
                    }, getOffset = { newSize ->
                        Offset(size.width - newSize.width, size.height - newSize.height)
                    }, color = Color.White)
            ) {
                content()
            }
        }
    }
}

/**
 * Modifier extension function which helps reduce boilerplate while drawing a decorative pokeball in composable backgrounds.
 *
 * @param color color of the pokeball, will be copied and have the [alpha] applied to it.
 * @param alpha alpha of the [color] provided.
 * @param getSize should return the desired size of the pokeball drawn, passes the pokeballs aspect ratio to help avoid distortion.
 * @param getOffset should return the desired offset of the pokeball, given it starts at coordinates (0,0) in the [DrawScope].
 * Passes a size value specified in the [getSize] lambda.
 */
@Composable
fun Modifier.drawPokeballBehind(
    color: Color = Color.Black,
    alpha: Float = 0.2f,
    getSize: DrawScope.(heightRatio: Float, widthRatio: Float) -> Size,
    getOffset: DrawScope.(size: Size) -> Offset
): Modifier {
    //Load pokeball resource
    val painter = painterResource(id = R.drawable.pokeball)
    return this then Modifier.drawBehind {
        with(painter) {
            //Calculate width/height ratio
            val heightRatio = intrinsicSize.height / intrinsicSize.width
            val widthRatio = intrinsicSize.width / intrinsicSize.height

            //Calculate new size via lambda
            val newSize = getSize(heightRatio, widthRatio)

            val offset = getOffset(newSize)
            translate(left = offset.x, top = offset.y) {
                //Draw with specified size, color and alpha
                draw(
                    size = Size(newSize.width, newSize.height),
                    colorFilter = ColorFilter.tint(color),
                    alpha = alpha
                )
            }
        }
    }
}