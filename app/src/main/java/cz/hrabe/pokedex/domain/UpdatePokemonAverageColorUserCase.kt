package cz.hrabe.pokedex.domain

import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import cz.hrabe.pokedex.data.local.PokemonDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdatePokemonAverageColorUserCase @Inject constructor(private val pokemonDao: PokemonDao) {

    suspend operator fun invoke(pokemon: Pokemon, drawable: Drawable) {
        if (pokemon.averageColor != null) return

        CoroutineScope(Dispatchers.Default).launch {
            try {
                val bitmap = drawable.toBitmap()
                val bitmapCopy = bitmap.copy(bitmap.config, true)
                val paletteBuilder = Palette.from(bitmapCopy)
                val palette = paletteBuilder.generate()

                val defaultColor = 0x000000

                val colorInt = palette.getDominantColor(defaultColor)
                if (colorInt != defaultColor) {
                    val fixedColor = colorInt.toUInt().toString(16)
                    withContext(Dispatchers.IO) {
                        pokemonDao.updateAvgColor(pokemon.id, fixedColor)

                    }
                    Log.d("TAG", "$fixedColor for ${pokemon.name}")
                } else {
                    Log.d("TAG", "Error getting dominant color")
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error", e)
            }
        }
    }
}