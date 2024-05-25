package cz.hrabe.pokedex.domain.model

import java.math.RoundingMode

data class Pokemon(
    /**
     * Pokemon's identifier
     */
    val id: Int,
    /**
     * Pokemon's name
     */
    val name: String,
    /**
     * URL pointing to an image of the Pokemon
     */
    val imageUrl: String,
    /**
     * Height in decimeters
     */
    val height: Int,
    /**
     * Weight in hectograms
     */
    val weight: Int,
    /**
     * Pokemon's type tags
     */
    val types: List<String>,
    /**
     * Base experience gained for defeating the Pokemon
     */
    val baseExperience: Int
) {

    /**
     * Pokemon's weight in kilograms
     */
    val weightKg: Double
        get() = weight / 10.0

    /**
     * Pokemon's weight in lbs
     *
     * @param decimals number of decimals
     * @param roundingMode see [RoundingMode]
     */
    fun weightLbs(decimals: Int = 1, roundingMode: RoundingMode = RoundingMode.HALF_UP): Double {
        val lbs = weightKg * 2.205
        val roundedLbs =
            lbs.toBigDecimal().setScale(decimals, roundingMode).toDouble()
        return roundedLbs
    }

    /**
     * Pokemon's height in inches
     */
    val heightIn: Double
        get() = heightCm / 2.54

    /**
     * Pokemon's height in centimeters
     */
    val heightCm: Double
        get() = height * 10.0

    /**
     * Return's a text representation of height in feet and inches
     *
     * Example: 2' 7"
     *
     * @param decimals number of decimals for inches
     */
    fun heightFtIn(decimals:Int): String {
        val feet = (heightIn / 12).toInt()
        val leftover = heightIn % 12

        return "$feet\' ${"%.${decimals}f".format(leftover)}\""
    }

}