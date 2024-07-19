package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestCaseDTOTest {
    @Test
    fun testTestCaseDTO() {
        val testCaseDTO = TestCaseDTO("Test Case", listOf("input"), listOf("output"), "variable", "id")
        assertEquals("Test Case", testCaseDTO.name)
        assertEquals(listOf("input"), testCaseDTO.input)
        assertEquals(listOf("output"), testCaseDTO.output)
        assertEquals("variable", testCaseDTO.envVars)
        assertEquals("id", testCaseDTO.snippetId)
    }
}
