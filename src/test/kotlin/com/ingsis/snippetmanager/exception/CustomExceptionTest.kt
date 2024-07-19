package com.ingsis.snippetmanager.exception

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CustomExceptionTest {
    @Test
    fun testCustomExceptionHandler() {
        val customExceptionHandler = CustomExceptionHandler()
        val notFoundException = NotFoundException("Resource not found")
        val serviceUnavailableException = ServiceUnavailableException("Service unavailable")
        val notFoundResponse = customExceptionHandler.handleNotFoundException(notFoundException)
        val serviceUnavailableResponse = customExceptionHandler.handleServiceUnavailableException(serviceUnavailableException)
        assertEquals(404, notFoundResponse.statusCodeValue)
        assertEquals(503, serviceUnavailableResponse.statusCodeValue)
    }

    @Test
    fun testErrorResponse() {
        val errorResponse = ErrorResponse(404, "Resource not found")
        assertEquals(404, errorResponse.status)
        assertEquals("Resource not found", errorResponse.message)
    }
}
