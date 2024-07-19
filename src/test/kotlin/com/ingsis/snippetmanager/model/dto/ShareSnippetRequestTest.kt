package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ShareSnippetRequestTest {
    @Test
    fun testShareSnippetRequest() {
        val shareSnippetRequest = ShareSnippetRequest("userId")
        assertEquals("userId", shareSnippetRequest.userId)
    }
}
