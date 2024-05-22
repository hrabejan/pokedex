package cz.hrabe.pokedex.domain

import java.math.RoundingMode

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
    val averageColor: Int?,
    val baseExperience: Int
) {

    val weightKg: Double
        get() = weight / 10.0

    fun weightLbs(decimals: Int = 1, roundingMode: RoundingMode = RoundingMode.HALF_UP): Double {
        val lbs = weightKg * 2.205
        val roundedLbs =
            lbs.toBigDecimal().setScale(decimals, roundingMode).toDouble()
        return roundedLbs
    }

    val heightIn: Double
        get() = heightCm / 2.54

    val heightCm: Double
        get() = height * 10.0

    /**
     * Return's a text representation of height in feet and inches
     *
     * Example: 2' 7"
     */
    fun heightFtIn(): String {
        val feet = (heightIn / 12).toInt()
        val leftover = heightIn % 12

        return "$feet\' ${"%.1f".format(leftover)}\""
    }

}