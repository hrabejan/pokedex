package cz.hrabe.pokedex.data.local.converters

import org.junit.Assert.*

import org.junit.Test

class TagsConverterTest {
    private val tagsConverter = TagsConverter()
    private val convertedString = "first,second,third"
    private val tags = listOf("first", "second", "third")

    @Test
    fun tagsToString_convertsTagsCorrectly() {
        val result = tagsConverter.tagsToString(tags)

        assertEquals(convertedString, result)
    }

    @Test
    fun tagsFromString_returnsExpectedList() {
        val convertedTags = tagsConverter.tagsFromString(convertedString)
        assertTrue(convertedTags.size == 3)
        convertedTags.forEachIndexed { index, tag ->
            assertEquals(tags[index], tag)
        }
    }
}