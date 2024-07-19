package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.CreateSnippetRequest
import com.ingsis.snippetmanager.model.dto.ShareSnippetRequest
import com.ingsis.snippetmanager.model.dto.SnippetDto
import com.ingsis.snippetmanager.model.dto.UpdateSnippetRequest
import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import com.ingsis.snippetmanager.service.SnippetService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.anyString
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.jwt.Jwt

@ExtendWith(MockitoExtension::class)
class SnippetControllerTest {
    @Mock
    private lateinit var snippetService: SnippetService

    @InjectMocks
    private lateinit var snippetController: SnippetController

    private lateinit var jwt: Jwt
    private val snippetDto = SnippetDto("1", "Test Snippet", "content", "print-script", ".ps", "Author", ComplianceEnum.COMPLIANT)
    private val createSnippetRequest = CreateSnippetRequest("Test Snippet", "Content", "print-script", ".ps")
    private val updateSnippetRequest = UpdateSnippetRequest("Updated Content")
    private val shareSnippetRequest = ShareSnippetRequest("UserId")

    @BeforeEach
    fun setup() {
        jwt =
            Jwt
                .withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "author")
                .build()
    }

    @Test
    fun `test 001 - should create a snippet`() {
        given(snippetService.saveSnippet(createSnippetRequest, jwt.subject)).willReturn(snippetDto)

        val response: ResponseEntity<SnippetDto> = snippetController.createSnippet(jwt, createSnippetRequest)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDto, response.body)
    }

    @Test
    fun `test 002 - should get all snippets`() {
        val snippetList = listOf(snippetDto)
        given(snippetService.getAllSnippets(anyString(), anyString())).willReturn(snippetList)

        val response: ResponseEntity<List<SnippetDto>> = snippetController.getAllSnippets(jwt, "Test Snippet")
        print(response)
        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(snippetDto), response.body)
    }

    @Test
    fun `test 003 - should get all snippets with error`() {
        given(snippetService.getAllSnippets(anyString(), anyString())).willThrow(RuntimeException("Error fetching snippets"))

        val exception =
            assertThrows<RuntimeException> {
                snippetController.getAllSnippets(jwt, "Test Snippet")
            }

        assertEquals("Error fetching snippets", exception.message)
    }

    @Test
    fun `test 004 - should get a snippet`() {
        given(snippetService.getSnippet("1")).willReturn(snippetDto)

        val response: ResponseEntity<SnippetDto> = snippetController.getSnippet("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDto, response.body)
    }

    @Test
    fun `test 005 - should not found snippet`() {
        given(snippetService.getSnippet("1")).willThrow(RuntimeException("Snippet with id: 1 not found"))

        val exception =
            assertThrows<RuntimeException> {
                snippetController.getSnippet("1")
            }

        assertEquals("Snippet with id: 1 not found", exception.message)
    }

    @Test
    fun `test 006 - should update a snippet`() {
        given(snippetService.updateSnippet(updateSnippetRequest, "1")).willReturn(snippetDto)

        val response: ResponseEntity<SnippetDto> = snippetController.updateSnippet("1", updateSnippetRequest)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDto, response.body)
    }

    @Test
    fun `test 007 - should not update snippet`() {
        given(snippetService.updateSnippet(updateSnippetRequest, "1")).willThrow(RuntimeException("Error updating snippet"))

        val exception =
            assertThrows<RuntimeException> {
                snippetController.updateSnippet("1", updateSnippetRequest)
            }

        assertEquals("Error updating snippet", exception.message)
    }

    @Test
    fun `test 008 - should share a snippet`() {
        given(snippetService.shareSnippet(shareSnippetRequest, "1")).willReturn(snippetDto)

        val response: ResponseEntity<SnippetDto> = snippetController.shareSnippet("1", shareSnippetRequest)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDto, response.body)
    }

    @Test
    fun `test 009 - should not share the snippet`() {
        given(snippetService.shareSnippet(shareSnippetRequest, "1")).willThrow(RuntimeException("Error sharing snippet"))

        val exception =
            assertThrows<RuntimeException> {
                snippetController.shareSnippet("1", shareSnippetRequest)
            }

        assertEquals("Error sharing snippet", exception.message)
    }

    @Test
    fun `test 010 - should delete a snippet`() {
        val response: ResponseEntity<Unit> = snippetController.deleteSnippet("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}
