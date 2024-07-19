package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.SnippetContentDTO
import com.ingsis.snippetmanager.service.RunnerService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.jwt.Jwt

@ExtendWith(MockitoExtension::class)
class RunnerControllerTest {
    @Mock
    private lateinit var runnerService: RunnerService

    @InjectMocks
    private lateinit var runnerController: RunnerController

    @Test
    fun `test 001 - should format snippet`() {
        val snippetContentDTO = SnippetContentDTO(content = "content")
        val jwt: Jwt =
            Jwt
                .withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user-id")
                .build()

        val expectedResponse = "formattedContent"
        given(runnerService.formatSnippet(snippetContentDTO, jwt.subject, jwt.tokenValue))
            .willReturn(expectedResponse)

        val response: ResponseEntity<String> = runnerController.formatSnippet(jwt, snippetContentDTO)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(expectedResponse, response.body)
    }

    @Test
    fun `test 002 - should handle formatting error`() {
        val snippetContentDTO = SnippetContentDTO(content = "content")
        val jwt: Jwt =
            Jwt
                .withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user-id")
                .build()

        given(runnerService.formatSnippet(snippetContentDTO, jwt.subject, jwt.tokenValue))
            .willThrow(RuntimeException("Error formatting snippet"))

        val exception =
            assertThrows<RuntimeException> {
                runnerController.formatSnippet(jwt, snippetContentDTO)
            }

        assertEquals("Error formatting snippet", exception.message)
    }
}
