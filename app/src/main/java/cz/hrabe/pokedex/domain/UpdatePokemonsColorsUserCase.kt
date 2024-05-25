package cz.hrabe.pokedex.domain

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toColorInt
import androidx.palette.graphics.Palette
import cz.hrabe.pokedex.data.local.PokemonColorDao
import cz.hrabe.pokedex.data.local.model.PokemonColorEntity
import cz.hrabe.pokedex.domain.model.Pokemon
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdatePokemonsColorsUserCase @Inject constructor(
    private val pokemonColorDao: PokemonColorDao,
    private val getPokemonsContrastColorUseCase: GetPokemonsContrastColorUseCase
) {

    suspend operator fun invoke(pokemonId: Int, drawable: Drawable) {

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val bitmap = drawable.toBitmap()
                val bitmapCopy = bitmap.copy(bitmap.config, true)
                val paletteBuilder = Palette.from(bitmapCopy)
                val palette = paletteBuilder.generate()

                val defaultColor = 0x000000

                val colorInt = palette.getDominantColor(defaultColor)
                if (colorInt != defaultColor) {
                    val contrastColor = getPokemonsContrastColorUseCase(colorInt)

                    withContext(Dispatchers.IO) {
                        pokemonColorDao.upsert(
                            PokemonColorEntity(
                                pokemonId,
                                colorInt,
                                contrastColor
                            )
                        )
                    }
                } else {
                    Log.d("TAG", "Error getting dominant color")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error", e)
            }
        }
    }
}