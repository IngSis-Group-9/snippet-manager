package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CreateSnippetRequestTest {
    @Test
    fun testCreateSnippetRequest() {
        val createSnippetRequest = CreateSnippetRequest("name", "content", "language", "extension")
        assertEquals("name", createSnippetRequest.name)
        assertEquals("content", createSnippetRequest.content)
        assertEquals("language", createSnippetRequest.language)
        assertEquals("extension", createSnippetRequest.extension)
    }
}
