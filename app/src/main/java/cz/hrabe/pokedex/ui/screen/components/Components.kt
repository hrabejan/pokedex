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
import androidx.compose.ui.unit.sp

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
            Type(name = type.replaceFirstChar { it.uppercase() }, textStyle = textStyle, color = color)
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

@Composable
fun NumberHeader(modifier: Modifier, number: Int) {
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