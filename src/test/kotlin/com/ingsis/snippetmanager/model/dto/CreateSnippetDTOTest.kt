package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateSnippetDTOTest {
    @Test
    fun testCreateSnippetRequest() {
        val createSnippetRequest = CreateSnippetDTO("name", "content", "language", "extension")
        assertEquals("name", createSnippetRequest.name)
        assertEquals("content", createSnippetRequest.content)
        assertEquals("language", createSnippetRequest.language)
        assertEquals("extension", createSnippetRequest.extension)
    }
}
