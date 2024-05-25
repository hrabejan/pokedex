package cz.hrabe.pokedex.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import cz.hrabe.pokedex.domain.model.Pokemon
import cz.hrabe.pokedex.domain.model.PokemonColors
import cz.hrabe.pokedex.ui.screen.components.TypeList
import cz.hrabe.pokedex.ui.theme.spacing

/**
 * Detail screen containing more extensive information about a Pokemon
 *
 * @param onBackPressed back button callback
 */
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailScreenViewModel: DetailScreenViewModel,
    onBackPressed: () -> Unit
) {

    val uiState by detailScreenViewModel.detailUiState.collectAsState()

    Scaffold {
        //Display a progress indicator if the Pokemon is not yet present
        if (uiState.pokemon == null) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            PokemonDetail(
                pokemon = uiState.pokemon!!.pokemon,
                //Provide default surface theme colors if pokemon colors do not exist
                pokemonColors = uiState.pokemon!!.pokemonColors ?: PokemonColors(
                    MaterialTheme.colorScheme.background,
                    MaterialTheme.colorScheme.onBackground
                ),
                modifier = modifier.padding(it)
            ) {
                onBackPressed()
            }
        }
    }
}

@Composable
fun PokemonDetail(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    pokemonColors: PokemonColors,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(pokemonColors.averageColor)
    ) {

        DetailTopBar(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.large),
            onBackPressed = onBackPressed,
            pokemonName = pokemon.name,
            contentColor = pokemonColors.contrastColor
        )

        Column(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStartPercent = 8,
                        topEndPercent = 8
                    )
                )
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize()
                .padding(MaterialTheme.spacing.extraLarge)
        ) {
            Text(
                text = LoremIpsum(25).values.joinToString(
                    separator = " ",
                    postfix = "."
                ) { it })

            BodyInfo(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.extraLarge),
                "${pokemon.weightLbs()} lbs (${pokemon.weightKg} kg)",
                "${pokemon.heightFtIn()} (${pokemon.heightCm} cm)"
            )

            InfoSection(modifier = Modifier, title = "Info") {
                InfoRow(title = "Base exp") {
                    Text(
                        text = AnnotatedString("${pokemon.baseExperience} ") + AnnotatedString(
                            text = "exp",
                            spanStyle = SpanStyle(fontWeight = FontWeight.ExtraBold)
                        ), style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                InfoRow(title = "Species") {
                    Text(text = "Monster", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                InfoRow(title = "Forms count") {
                    Text(text = "6", style = MaterialTheme.typography.bodyMedium)
                }
            }

            InfoSection(
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.large),
                title = "Type"
            ) {
                TypeList(
                    textStyle = MaterialTheme.typography.bodyLarge,
                    types = pokemon.types,
                    orientation = Orientation.Horizontal,
                    color = Color.Gray
                )
            }
        }
    }
}

/**
 * Element describing a specific section of information.
 * Contains a title header at the top and section's content below it.
 *
 * @param title header text
 * @param content content below the header
 */
@Composable
private fun InfoSection(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier) {
        Heading(text = title)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
        content()
    }
}

/**
 * Row meant mostly for text based information.
 * Displays information's title at the start and the content on it's right.
 * The alignment of this row's content is Start/Top.
 *
 * @param title info's title
 * @param content content to be displayed to the right of title
 */
@Composable
fun InfoRow(modifier: Modifier = Modifier, title: String, content: @Composable () -> Unit) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        InfoItemTitle(text = title, modifier = Modifier.weight(2f))
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.medium))
        Box(modifier = Modifier.weight(3f)) {
            content()
        }
    }
}

/**
 * Details screen's top bar
 *
 * Displays the name of a Pokemon and a back button.
 *
 * @param contentColor the color of content such as the name and back button
 * @param pokemonName Pokemon's name
 * @param onBackPressed back button callback
 */
@Composable
private fun DetailTopBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    contentColor: Color,
    pokemonName: String
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(125.dp)
    ) {

        Box(modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .clickable {
                onBackPressed()
            }
            .align(Alignment.CenterStart)
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back icon",
                tint = contentColor
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = pokemonName.replaceFirstChar { it.uppercase() },
            color = contentColor,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

/**
 * A card with the specific purpose of displaying pokemon's weight and height.
 * Draws a row containing two equally sized columns inside it that show a height/weight with a respective title above them.
 *
 * @param weight pokemon's weight
 * @param height pokemon's height
 */
@Composable
private fun BodyInfo(modifier: Modifier = Modifier, weight: String, height: String) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
    ) {
        Row(modifier = Modifier.padding(MaterialTheme.spacing.large)) {
            BodyInfoColumn(Modifier.weight(1f), text = weight, title = "Weight")
            BodyInfoColumn(Modifier.weight(1f), text = height, title = "Height")
        }
    }
}

/**
 * A single column containing a title at the top and text below it
 */
@Composable
private fun BodyInfoColumn(modifier: Modifier, title: String, text: String) {
    Column(modifier = modifier) {
        InfoItemTitle(text = title)
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun InfoItemTitle(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier, text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.ExtraBold,
        color = Color.Gray
    )
}

@Preview
@Composable
fun PokemonDetailPreview() {
    PokemonDetail(
        pokemon = Pokemon(
            1,
            "Pikachu",
            "",
            5,
            1,
            listOf("grass", "water"),
            110
        ), pokemonColors = PokemonColors(averageColor = Color.Black, Color.White)
    ) {}
}

@Composable
fun Heading(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.ExtraBold,
        style = MaterialTheme.typography.bodyLarge
    )
}