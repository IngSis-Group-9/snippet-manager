package com.ingsis.snippetmanager.model.entity

import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SnippetTest {
    @Test
    fun testSnippet() {
        val user =
            User(
                id = "1",
                name = "testuser",
                email = "testuser@example.com",
            )
        val snippet = Snippet("Test Snippet", "print-script", ".ps", user, ComplianceEnum.COMPLIANT)
        assertEquals("Test Snippet", snippet.name)
        assertEquals("print-script", snippet.language)
        assertEquals(".ps", snippet.extension)
        assertEquals(ComplianceEnum.COMPLIANT, snippet.compliance)
    }
}
