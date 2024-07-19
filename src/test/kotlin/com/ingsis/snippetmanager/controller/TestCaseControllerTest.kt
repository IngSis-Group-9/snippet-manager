package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.TestCaseDTO
import com.ingsis.snippetmanager.model.entity.Snippet
import com.ingsis.snippetmanager.model.entity.TestCase
import com.ingsis.snippetmanager.model.entity.User
import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import com.ingsis.snippetmanager.service.TestCaseService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus

@ExtendWith(MockitoExtension::class)
class TestCaseControllerTest {
    @Mock
    private lateinit var testCaseService: TestCaseService

    @InjectMocks
    private lateinit var testCaseController: TestCaseController

    private lateinit var user: User
    private lateinit var snippet: Snippet
    private lateinit var testCase: TestCase
    private lateinit var testCaseDTO: TestCaseDTO

    @BeforeEach
    fun setup() {
        user =
            User(
                id = "1",
                name = "testuser",
                email = "testuser@example.com",
            )

        snippet =
            Snippet(
                name = "Test Snippet",
                language = "print-script",
                extension = ".ps",
                owner = user,
                compliance = ComplianceEnum.COMPLIANT,
            )

        user.ownedSnippets.plus(snippet)

        testCaseDTO =
            TestCaseDTO(
                name = "Test Case",
                input = listOf("input1", "input2"),
                output = listOf("output1", "output2"),
                envVars = "VAR1: value1, VAR2: value2",
                snippetId = "1",
            )

        testCase =
            TestCase(
                name = "Test Case",
                input = listOf("input1", "input2"),
                output = listOf("output1", "output2"),
                envVars = mapOf("VAR1" to "value1", "VAR2" to "value2"),
                snippet = snippet,
            )
    }

    @Test
    fun `test 001 - should create a test case`() {
        given(testCaseService.createTestCase(testCaseDTO)).willReturn(testCase)

        val response: TestCase = testCaseController.createTestCase(testCaseDTO)

        assertNotNull(response)
        assertEquals(testCase, response)
    }

    @Test
    fun `test 002 - should not create a test case`() {
        given(testCaseService.createTestCase(testCaseDTO)).willThrow(RuntimeException("Error creating test case"))

        val exception =
            assertThrows<RuntimeException> {
                testCaseController.createTestCase(testCaseDTO)
            }

        assertEquals("Error creating test case", exception.message)
    }

    @Test
    fun `test 003 - should get a test case by snippet id`() {
        val testCases = listOf(testCase)
        given(testCaseService.getTestCasesBySnippetId("1")).willReturn(testCases)

        val response = testCaseController.getTestCasesBySnippetId("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(testCases, response.body)
    }

    @Test
    fun `test 004 - should not get a test case by snippet id`() {
        given(testCaseService.getTestCasesBySnippetId("1")).willThrow(RuntimeException("Error fetching test cases"))

        val exception =
            assertThrows<RuntimeException> {
                testCaseController.getTestCasesBySnippetId("1")
            }

        assertEquals("Error fetching test cases", exception.message)
    }

    @Test
    fun `test 005 - should remove a test case`() {
        given(testCaseService.removeTestCase("1")).willReturn("Test case removed successfully")

        val response = testCaseController.removeTestCase("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals("Test case removed successfully", response.body)
    }

    @Test
    fun `test 006 - should test remove a test case with an error`() {
        given(testCaseService.removeTestCase("1")).willThrow(RuntimeException("Error removing test case"))

        val exception =
            assertThrows<RuntimeException> {
                testCaseController.removeTestCase("1")
            }

        assertEquals("Error removing test case", exception.message)
    }
}
