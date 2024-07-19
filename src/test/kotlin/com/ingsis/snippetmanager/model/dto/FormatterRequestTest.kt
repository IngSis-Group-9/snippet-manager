package com.ingsis.snippetmanager.model.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FormatterRequestTest {
    @Test
    fun testFormatterRequest() {
        val formatterRequest = FormatterRequest("snippet", FormatterRulesDTO(true))
        assertEquals("snippet", formatterRequest.snippet)
        assertEquals(true, formatterRequest.formatterRules.spaceBeforeColon)
    }
}
