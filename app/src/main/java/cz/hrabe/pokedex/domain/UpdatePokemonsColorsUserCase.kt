package cz.hrabe.pokedex.domain

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.toColorInt
import androidx.palette.graphics.Palette
import cz.hrabe.pokedex.data.local.PokemonColorDao
import cz.hrabe.pokedex.data.local.PokemonColorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdatePokemonsColorsUserCase @Inject constructor(
    private val pokemonColorDao: PokemonColorDao,
    private val getPokemonsContrastColorUseCase: GetPokemonsContrastColorUseCase
) {

    suspend operator fun invoke(pokemon: Pokemon, drawable: Drawable) {

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val bitmap = drawable.toBitmap()
                val bitmapCopy = bitmap.copy(bitmap.config, true)
                val paletteBuilder = Palette.from(bitmapCopy)
                val palette = paletteBuilder.generate()

                val defaultColor = 0x000000

                val colorInt = palette.getDominantColor(defaultColor)
                if (colorInt != defaultColor) {
                    val resultColorString = "#${colorInt.toUInt().toString(16)}".toColorInt()
                    val resultColor = Color(resultColorString)
                    val contrastColor = getPokemonsContrastColorUseCase(resultColor)

                    withContext(Dispatchers.IO) {
                        pokemonColorDao.upsert(
                            PokemonColorEntity(
                                pokemon.id,
                                resultColor,
                                contrastColor
                            )
                        )
                    }
                    Log.d("TAG", "$resultColorString for ${pokemon.name}")
                } else {
                    Log.d("TAG", "Error getting dominant color")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error", e)
            }
        }
    }
}