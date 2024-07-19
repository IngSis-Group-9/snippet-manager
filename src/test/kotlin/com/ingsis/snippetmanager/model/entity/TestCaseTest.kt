package com.ingsis.snippetmanager.model.entity

import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestCaseTest {
    @Test
    fun testTestCase() {
        val user =
            User(
                id = "1",
                name = "testuser",
                email = "testuser@example.com",
            )

        val snippet =
            Snippet(
                name = "Test Snippet",
                language = "print-script",
                extension = ".ps",
                owner = user,
                compliance = ComplianceEnum.COMPLIANT,
            )
        val testCase = TestCase("Test Case", listOf("input"), listOf("output"), mapOf(), snippet)
        assertEquals("Test Case", testCase.name)
        assertEquals("Test Snippet", testCase.snippet.name)
        assertEquals("input", testCase.input[0])
    }
}
