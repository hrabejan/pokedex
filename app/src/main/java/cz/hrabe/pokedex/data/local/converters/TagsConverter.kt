package cz.hrabe.pokedex.data.local.converters

import androidx.room.TypeConverter

/**
 * Class used for converting Pokemon's tags data between a List of Strings and a single String
 */
class TagsConverter {

    /**
     * Flattens the list of Pokemon's tags into a single string, separated by ',' character.
     * Ex.: "tag1,tag2,tag3"
     */
    @TypeConverter
    fun tagsToString(tags: List<String>): String {
        return tags.joinToString(",")
    }

    /**
     * Converts a string of Pokemon's tags separated by ',' character into a List with these types separated
     */
    @TypeConverter
    fun tagsFromString(value: String): List<String> {
        return value.split(",")
    }
}