package cz.hrabe.pokedex.data.local.converters

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter


class ColorConverter {

    @TypeConverter
    fun colorToString(color: Color): String {
        return with(color) {
            "$red,$green,$blue"
        }
    }

    @TypeConverter
    fun stringToColor(string: String): Color {
        val splitString = string.split(",")
        return with(splitString) { Color(get(0).toFloat(), get(1).toFloat(), get(3).toFloat()) }
    }
}