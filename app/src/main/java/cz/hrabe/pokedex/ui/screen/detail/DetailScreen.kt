package cz.hrabe.pokedex.ui.screen.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import cz.hrabe.pokedex.domain.Pokemon
import cz.hrabe.pokedex.domain.utils.getContrastColor
import cz.hrabe.pokedex.ui.screen.components.LocalPokemonColors
import cz.hrabe.pokedex.ui.screen.components.PokemonColors
import cz.hrabe.pokedex.ui.screen.components.TypeList
import cz.hrabe.pokedex.ui.theme.spacing

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    detailScreenViewModel: DetailScreenViewModel,
    onBackPressed: () -> Unit
) {

    val uiState by detailScreenViewModel.detailUiState.collectAsState()

    Scaffold {
        if (uiState.pokemon == null) {
            Box(
                modifier = modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            PokemonDetail(pokemon = uiState.pokemon!!, modifier = modifier.padding(it)) {
                onBackPressed()
            }
        }
    }
}

@Composable
fun PokemonDetail(modifier: Modifier = Modifier, pokemon: Pokemon, onBackPressed: () -> Unit) {
    val backgroundColor =
        pokemon.averageColor?.let { Color(it) } ?: MaterialTheme.colorScheme.primary
    CompositionLocalProvider(
        LocalPokemonColors provides PokemonColors(
            backgroundColor,
            backgroundColor.getContrastColor()
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {

            DetailTopBar(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.large),
                onBackPressed,
                pokemon
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
                    pokemon.weight,
                    pokemon.height
                )

                InfoParagraph(modifier = Modifier, title = "Info") {
                    Row(modifier = Modifier.fillMaxWidth(0.5f), verticalAlignment = Alignment.CenterVertically) {
                        BodyInfoTitle(modifier = Modifier.weight(1f),text = "Base exp")
                        Text(modifier = Modifier.weight(1f),text = "40exp")
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    Row(modifier = Modifier.fillMaxWidth(0.5f), verticalAlignment = Alignment.CenterVertically) {
                        BodyInfoTitle(modifier = Modifier.weight(1f),text = "Species")
                        Text(modifier = Modifier.weight(1f),text = "Monster")
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    Row(modifier = Modifier.fillMaxWidth(0.5f), verticalAlignment = Alignment.CenterVertically) {
                        BodyInfoTitle(modifier = Modifier.weight(1f),text = "Forms count")
                        Text(modifier = Modifier.weight(1f),text = "6")
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.large))
                }

                InfoParagraph(modifier = Modifier, title = "Type") {
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
}

@Composable
private fun InfoParagraph(
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

@Composable
fun InfoRow(
    modifier: Modifier = Modifier,
    titleContent: @Composable () -> Unit,
    textContent: @Composable () -> Unit
) {
    Row(modifier = modifier) {

    }
}

@Composable
fun InfoRow(modifier: Modifier = Modifier, title: String, text: String) {
    Row(modifier = modifier) {

        Text(text = title)
        Text(text = text)
    }
}

@Composable
private fun DetailTopBar(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    pokemon: Pokemon
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
                tint = LocalPokemonColors.current.contrastColor
            )
        }
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = pokemon.name.replaceFirstChar { it.uppercase() },
            color = LocalPokemonColors.current.contrastColor,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.ExtraBold
        )
    }
}

@Composable
private fun BodyInfo(modifier: Modifier = Modifier, weight: Int, height: Int) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 15.dp)
    ) {
        Row(modifier = Modifier.padding(MaterialTheme.spacing.large)) {
            BodyInfoColumn(Modifier.weight(1f), weight.toString())
            BodyInfoColumn(Modifier.weight(1f), height.toString())
        }
    }
}

@Composable
private fun BodyInfoColumn(modifier: Modifier, text: String) {
    Column(modifier = modifier) {
        BodyInfoTitle(text = "Weight")
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
        Text(text = text)
    }
}

@Composable
private fun BodyInfoTitle(modifier: Modifier = Modifier, text: String) {
    Text(
        modifier = modifier, text = text,
        style = MaterialTheme.typography.bodySmall,
        fontWeight = FontWeight.Bold,
        color = Color.Gray
    )
}

@Preview
@Composable
fun PokemonDetailPreview() {
    PokemonDetail(pokemon = Pokemon(1, "Pikachu", "", 5, 1, listOf("grass", "water"), 0x00FF00)) {}
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