package com.ingsis.snippetmanager.model.dto

import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnippetDtoTest {
    @Test
    fun testSnippetDto() {
        val snippetDto = SnippetDto("id", "name", "content", "language", "extension", "user", ComplianceEnum.COMPLIANT)
        assertEquals("id", snippetDto.id)
        assertEquals("name", snippetDto.name)
        assertEquals("content", snippetDto.content)
        assertEquals("language", snippetDto.language)
        assertEquals("extension", snippetDto.extension)
        assertEquals("user", snippetDto.author)
    }
}
