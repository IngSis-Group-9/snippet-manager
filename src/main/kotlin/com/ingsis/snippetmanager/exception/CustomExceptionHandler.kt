package com.ingsis.snippetmanager.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.message ?: "Resource not found")
        return ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ServiceUnavailableException::class)
    fun handleServiceUnavailableException(ex: ServiceUnavailableException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.message ?: "Service unavailable")
        return ResponseEntity(errorResponse, HttpStatus.SERVICE_UNAVAILABLE)
    }
}

data class ErrorResponse(
    val status: Int,
    val message: String,
)
