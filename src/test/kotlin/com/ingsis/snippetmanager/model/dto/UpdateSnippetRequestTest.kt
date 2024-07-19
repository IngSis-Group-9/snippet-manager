package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UpdateSnippetRequestTest {
    @Test
    fun testUpdateSnippetRequest() {
        val updateSnippetRequest = UpdateSnippetRequest("content")
        assertEquals("content", updateSnippetRequest.content)
    }
}
