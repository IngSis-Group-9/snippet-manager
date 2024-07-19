package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TestCaseDTOTest {
    @Test
    fun testTestCaseDTO() {
        val testCaseDTO = TestCaseDTO("1", "Test Case", listOf("input"), listOf("output"), "variable", "snippetId")
        assertEquals("Test Case", testCaseDTO.name)
        assertEquals(listOf("input"), testCaseDTO.inputs)
        assertEquals(listOf("output"), testCaseDTO.outputs)
        assertEquals("variable", testCaseDTO.envVars)
        assertEquals("snippetId", testCaseDTO.snippetId)
    }
}
