package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UpdateSnippetDTOTest {
    @Test
    fun testUpdateSnippetDTO() {
        val updateSnippetRequest = UpdateSnippetDTO("1", "content")
        assertEquals("1", updateSnippetRequest.id)
        assertEquals("content", updateSnippetRequest.content)
    }
}
