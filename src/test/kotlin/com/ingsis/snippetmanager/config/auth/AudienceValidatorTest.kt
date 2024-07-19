package com.ingsis.snippetmanager.config.auth

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.oauth2.jwt.Jwt

class AudienceValidatorTest {
    private lateinit var audienceValidator: AudienceValidator
    private val audience = "expected-audience"

    @BeforeEach
    fun setUp() {
        audienceValidator = AudienceValidator(audience)
    }

    @Test
    fun `test 001 - should validate with valid audience`() {
        val jwt = Mockito.mock(Jwt::class.java)
        Mockito.`when`(jwt.audience).thenReturn(setOf(audience).toMutableList())

        val result = audienceValidator.validate(jwt)

        assertNotNull(result)
        assertFalse(result.hasErrors())
    }

    @Test
    fun `test 002 - should test validate with invalid audience`() {
        val jwt = Mockito.mock(Jwt::class.java)
        Mockito.`when`(jwt.audience).thenReturn(setOf("invalid-audience").toMutableList())

        val result = audienceValidator.validate(jwt)

        assertNotNull(result)
        val errors = result.errors
        assertEquals(1, errors.size)
        val error = errors.iterator().next()
        assertEquals("invalid_token", error.errorCode)
        assertEquals("The required audience is missing", error.description)
    }

    @Test
    fun `test 003 - should test validate with empty audience`() {
        val jwt = Mockito.mock(Jwt::class.java)
        Mockito.`when`(jwt.audience).thenReturn(emptyList())

        val result = audienceValidator.validate(jwt)

        assertNotNull(result)
        assertTrue(result.hasErrors())
        val errors = result.errors
        assertEquals(1, errors.size)
        val error = errors.iterator().next()
        assertEquals("invalid_token", error.errorCode)
        assertEquals("The required audience is missing", error.description)
    }

    @Test
    fun `test 004 - should test validate with null audience`() {
        val jwt = Mockito.mock(Jwt::class.java)

        val result = audienceValidator.validate(jwt)

        assertNotNull(result)
        assertTrue(result.hasErrors())
        val errors = result.errors
        assertEquals(1, errors.size)
        val error = errors.iterator().next()
        assertEquals("invalid_token", error.errorCode)
        assertEquals("The required audience is missing", error.description)
    }
}
