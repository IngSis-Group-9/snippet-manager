package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.ServiceUnavailableException
import com.ingsis.snippetmanager.model.dto.CreateSnippetDTO
import com.ingsis.snippetmanager.model.dto.UpdateSnippetDTO
import com.ingsis.snippetmanager.model.entity.BaseEntity
import com.ingsis.snippetmanager.model.entity.Snippet
import com.ingsis.snippetmanager.model.entity.User
import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import com.ingsis.snippetmanager.repository.SnippetRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class SnippetServiceTest {
    @Mock
    private lateinit var assetService: AssetService

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var snippetRepository: SnippetRepository

    @InjectMocks
    private lateinit var snippetService: SnippetService

    private val userId = "user1"
    private val snippetId = "snippet1"

    private val user =
        User(
            id = userId,
            name = "testuser",
            email = "testuser@example.com",
        )

    private val snippet =
        Snippet(
            name = "Snippet",
            language = "print-script",
            extension = ".ps",
            owner = user,
            compliance = ComplianceEnum.PENDING,
        )

    private fun setId(
        snippet: Snippet,
        id: String,
    ) {
        val idField = BaseEntity::class.java.getDeclaredField("id")
        idField.isAccessible = true
        idField.set(snippet, id)
    }

    @BeforeEach
    fun setup() {
        setId(snippet, snippetId)
    }

    @Test
    fun `test 001 - should return SnippetDto when saving a snippet`() {
        val request =
            CreateSnippetDTO(
                name = "Snippet",
                content = "snippet content",
                language = "print-script",
                extension = ".ps",
            )

        val response = ResponseEntity("snippet saved", HttpStatus.OK)

        `when`(userService.findUserById(userId)).thenReturn(user)
        `when`(snippetRepository.save(any(Snippet::class.java))).thenReturn(snippet)
        `when`(assetService.saveSnippet(snippetId, request.content)).thenReturn(response)

        val result = snippetService.saveSnippet(request, userId)

        assertEquals(snippetId, result.id)
        assertEquals("Snippet", result.name)
        assertEquals("snippet content", result.content)
    }

    @Test
    fun `test 002 - should throw ServiceUnavailableException when saving a snippet`() {
        val request =
            CreateSnippetDTO(
                name = "Snippet",
                content = "snippet content",
                language = "print-script",
                extension = ".ps",
            )

        val response = ResponseEntity("snippet not saved", HttpStatus.BAD_REQUEST)

        `when`(userService.findUserById(userId)).thenReturn(user)
        `when`(snippetRepository.save(any(Snippet::class.java))).thenReturn(snippet)
        `when`(assetService.saveSnippet(snippetId, request.content)).thenReturn(response)

        assertThrows(ServiceUnavailableException::class.java) {
            snippetService.saveSnippet(request, userId)
        }
    }

    @Test
    fun `test 003 - should return SnippetDto when getting a snippet`() {
        val response = ResponseEntity("snippet content", HttpStatus.OK)

        `when`(snippetRepository.findById(snippetId)).thenReturn(java.util.Optional.of(snippet))
        `when`(assetService.getSnippet(snippetId)).thenReturn(response)

        val result = snippetService.getSnippet(snippetId)

        assertEquals(snippetId, result.id)
        assertEquals("Snippet", result.name)
        assertEquals("snippet content", result.content)
    }

    @Test
    fun `test 004 - should throw ServiceUnavailableException when getting a snippet`() {
        val response = ResponseEntity("snippet not found", HttpStatus.BAD_REQUEST)

        `when`(snippetRepository.findById(snippetId)).thenReturn(java.util.Optional.of(snippet))
        `when`(assetService.getSnippet(snippetId)).thenReturn(response)

        assertThrows(ServiceUnavailableException::class.java) {
            snippetService.getSnippet(snippetId)
        }
    }

    @Test
    fun `test 005 - should return a list of snippetsDto when getting all snippets`() {
        val response = ResponseEntity("Snippet content", HttpStatus.OK)
        val snippets = listOf(snippet)

        `when`(userService.findUserById(userId)).thenReturn(user)
        `when`(snippetRepository.findAllByOwnerOrSharedWith(user, "Snippet")).thenReturn(snippets)
        `when`(assetService.getSnippet(snippetId)).thenReturn(response)

        val result = snippetService.getAllSnippets(userId, "Snippet")

        assertEquals(1, result.size)
        assertEquals(snippetId, result[0].id)
        assertEquals("Snippet content", result[0].content)
    }

    @Test
    fun `test 006 - should delete a snippet`() {
        snippetService.deleteSnippet(snippetId)

        verify(snippetRepository, times(1)).deleteById(snippetId)
    }

    @Test
    fun `test 007 - should return an updated SnippetDto on success`() {
        val request = UpdateSnippetDTO(snippetId, "Updated content")
        val responseSave = ResponseEntity("Updated content", HttpStatus.OK)
        val responseDelete = ResponseEntity("Snippet deleted", HttpStatus.OK)

        `when`(snippetRepository.findById(snippetId)).thenReturn(java.util.Optional.of(snippet))
        `when`(assetService.deleteSnippet(snippetId)).thenReturn(responseDelete)
        `when`(assetService.saveSnippet(snippetId, request.content)).thenReturn(responseSave)

        val result = snippetService.updateSnippet(request)

        assertEquals(snippetId, result.id)
        assertEquals("Updated content", result.content)
    }

    @Test
    fun `test 008 - updateSnippet should throw ServiceUnavailableException when delete fails`() {
        val request = UpdateSnippetDTO(snippetId, "Updated content")
        val responseDelete = ResponseEntity("Error", HttpStatus.INTERNAL_SERVER_ERROR)

        `when`(snippetRepository.findById(snippetId)).thenReturn(java.util.Optional.of(snippet))
        `when`(assetService.deleteSnippet(snippetId)).thenReturn(responseDelete)

        assertThrows(ServiceUnavailableException::class.java) {
            snippetService.updateSnippet(request)
        }
    }

    @Test
    fun `test 009 - shareSnippet should return a SnippetDTO on success`() {
        val shareId = "share-user-id"
        val shareUser = User(id = shareId, name = "Share User", email = "shareuser@example.com")
        `when`(snippetRepository.findById(snippetId)).thenReturn(Optional.of(snippet))
        `when`(userService.findUserById(shareId)).thenReturn(shareUser)
        `when`(snippetRepository.save(any<Snippet>())).thenReturn(snippet)
        `when`(assetService.getSnippet(snippetId)).thenReturn(ResponseEntity.ok("content"))

        val result = snippetService.shareSnippet(shareId, snippetId)

        assertEquals("snippet1", result.id)
        assertEquals("Snippet", result.name)
        assertEquals("content", result.content)
        assertEquals("print-script", result.language)
        assertEquals(".ps", result.extension)
        assertEquals(snippet.owner.name, result.author)
        assertEquals(snippet.compliance, result.compliance)
    }

    @Test
    fun `test 010 - setSnippetsComplianceToPending should update compliance to PENDING`() {
        val snippets = listOf(snippet)
        `when`(snippetRepository.findAllByOwnerId(userId)).thenReturn(snippets)
        `when`(snippetRepository.saveAll(snippets)).thenReturn(snippets)

        val result = snippetService.setSnippetsComplianceToPending(userId)

        assertEquals(ComplianceEnum.PENDING, result[0].compliance)
    }

    @Test
    fun `test 011 - updateSnippetCompliance should update the compliance of the snippet`() {
        val complianceEnum = ComplianceEnum.COMPLIANT
        `when`(snippetRepository.findByIdAndOwnerId(snippetId, userId)).thenReturn(Optional.of(snippet))
        `when`(snippetRepository.save(snippet)).thenReturn(snippet)

        snippetService.updateSnippetCompliance(snippetId, userId, complianceEnum)

        assertEquals(complianceEnum, snippet.compliance)
    }
}
