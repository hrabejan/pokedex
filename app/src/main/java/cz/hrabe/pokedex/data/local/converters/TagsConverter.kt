package cz.hrabe.pokedex.data.local.converters

import androidx.room.TypeConverter

class TagsConverter {

    @TypeConverter
    fun tagsToString(tags: List<String>): String {
        return tags.joinToString(",")
    }

    @TypeConverter
    fun tagsFromString(value: String): List<String> {
        return value.split(",")
    }
}