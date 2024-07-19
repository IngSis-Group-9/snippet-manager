package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.CreateSnippetDTO
import com.ingsis.snippetmanager.model.dto.SnippetDTO
import com.ingsis.snippetmanager.model.dto.UpdateSnippetDTO
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
    private val snippetDTO = SnippetDTO("1", "Test Snippet", "content", "print-script", ".ps", "Author", ComplianceEnum.COMPLIANT)
    private val createSnippetDTO = CreateSnippetDTO("Test Snippet", "Content", "print-script", ".ps")
    private val updateSnippetRequest = UpdateSnippetDTO("1", "Updated Content")

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
        given(snippetService.saveSnippet(createSnippetDTO, jwt.subject)).willReturn(snippetDTO)

        val response = snippetController.createSnippet(jwt, createSnippetDTO)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDTO, response.body)
    }

    @Test
    fun `test 002 - should get all snippets`() {
        val snippetList = listOf(snippetDTO)
        given(snippetService.getAllSnippets(anyString(), anyString())).willReturn(snippetList)

        val response: ResponseEntity<List<SnippetDTO>> = snippetController.getAllSnippets(jwt, "Test Snippet")
        print(response)
        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(snippetDTO), response.body)
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
        given(snippetService.getSnippet("1")).willReturn(snippetDTO)

        val response: ResponseEntity<SnippetDTO> = snippetController.getSnippet("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDTO, response.body)
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
        given(snippetService.updateSnippet(updateSnippetRequest)).willReturn(snippetDTO)

        val response: ResponseEntity<SnippetDTO> = snippetController.updateSnippet(updateSnippetRequest)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDTO, response.body)
    }

    @Test
    fun `test 007 - should not update snippet`() {
        given(snippetService.updateSnippet(updateSnippetRequest)).willThrow(RuntimeException("Error updating snippet"))

        val exception =
            assertThrows<RuntimeException> {
                snippetController.updateSnippet(updateSnippetRequest)
            }

        assertEquals("Error updating snippet", exception.message)
    }

    @Test
    fun `test 008 - should share a snippet`() {
        given(snippetService.shareSnippet("2", "1")).willReturn(snippetDTO)

        val response = snippetController.shareSnippet("1", "2")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(snippetDTO, response.body)
    }

    @Test
    fun `test 009 - should not share the snippet`() {
        given(snippetService.shareSnippet("2", "1")).willThrow(RuntimeException("Error sharing snippet"))

        val exception =
            assertThrows<RuntimeException> {
                snippetController.shareSnippet("1", "2")
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
