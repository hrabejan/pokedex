package cz.hrabe.pokedex.ui.screen.components

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TypeList(
    modifier: Modifier = Modifier,
    types: List<String>,
    orientation: Orientation = Orientation.Vertical,
    textStyle: TextStyle = TextStyle(),
    color: Color = Color.White
) {
    val spacerModifier =
        if (orientation == Orientation.Vertical) Modifier.height(8.dp) else Modifier.width(8.dp)
    val fill: @Composable () -> Unit = {
        for (type in types) {
            Type(
                name = type.replaceFirstChar { it.uppercase() },
                textStyle = textStyle,
                color = color
            )
            Spacer(modifier = spacerModifier)
        }
    }
    if (orientation == Orientation.Vertical) {
        Column(modifier = modifier) {
            fill()
        }
    } else {
        Row(modifier = modifier) {
            fill()
        }
    }
}

@Composable
fun Type(
    modifier: Modifier = Modifier,
    name: String,
    textStyle: TextStyle = TextStyle(),
    color: Color = Color.White
) {
    Surface(color = color.copy(alpha = 0.2f), shape = CircleShape, modifier = modifier) {
        Text(
            style = textStyle,
            text = name,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp),
            fontWeight = FontWeight.ExtraBold
        )
    }
}


/**
 * Element used to display Pokemon's identifier [number].
 * The number starts with a '#' character and has up to two leading zeros depending on the number of digits.
 *
 * @param number Pokemon's identifier
 */
@Composable
fun PokemonNumber(
    modifier: Modifier,
    number: Int,
    textStyle: TextStyle = TextStyle(
        color = Color.Black.copy(alpha = 0.2f),
        fontWeight = FontWeight.ExtraBold,
        fontSize = MaterialTheme.typography.bodyLarge.fontSize
    ),
    textAlign: TextAlign = TextAlign.End
) {
    val numberText = number.toString().padStart(3, '0')
    Text(
        text = "#$numberText",
        textAlign = textAlign,
        style = textStyle,
        modifier = modifier
    )
}