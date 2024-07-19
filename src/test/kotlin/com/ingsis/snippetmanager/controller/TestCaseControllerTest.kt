package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.CreateTestCaseDTO
import com.ingsis.snippetmanager.model.dto.TestCaseDTO
import com.ingsis.snippetmanager.model.dto.TestCaseResultDTO
import com.ingsis.snippetmanager.model.entity.Snippet
import com.ingsis.snippetmanager.model.entity.User
import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import com.ingsis.snippetmanager.service.TestCaseService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.jwt.Jwt

@ExtendWith(MockitoExtension::class)
class TestCaseControllerTest {
    @Mock
    private lateinit var testCaseService: TestCaseService

    @InjectMocks
    private lateinit var testCaseController: TestCaseController

    private lateinit var jwt: Jwt
    private lateinit var user: User
    private lateinit var snippet: Snippet
    private lateinit var testCaseDTO: TestCaseDTO
    private lateinit var createTestCaseDTO: CreateTestCaseDTO
    private lateinit var testCaseResultDTO: TestCaseResultDTO

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

        createTestCaseDTO =
            CreateTestCaseDTO(
                name = "Test Case",
                input = listOf("input1", "input2"),
                output = listOf("output1", "output2"),
                envVars = "VAR1: value1, VAR2: value2",
                snippetId = "1",
            )

        testCaseDTO =
            TestCaseDTO(
                id = "1",
                name = "Test Case",
                inputs = listOf("input1", "input2"),
                outputs = listOf("output1", "output2"),
                envVars = "VAR1: value1, VAR2: value2",
                snippetId = "1",
            )

        jwt =
            Jwt
                .withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "author")
                .build()

        testCaseResultDTO = TestCaseResultDTO(true)
    }

    @Test
    fun `test 001 - should create a test case`() {
        given(testCaseService.createTestCase(createTestCaseDTO)).willReturn(testCaseDTO)

        val response = testCaseController.createTestCase(createTestCaseDTO)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(testCaseDTO, response.body)
    }

    @Test
    fun `test 002 - should not create a test case`() {
        given(testCaseService.createTestCase(createTestCaseDTO)).willThrow(RuntimeException("Error creating test case"))

        val exception =
            assertThrows<RuntimeException> {
                testCaseController.createTestCase(createTestCaseDTO)
            }

        assertEquals("Error creating test case", exception.message)
    }

    @Test
    fun `test 003 - should get a test case by snippet id`() {
        val testCases = listOf(testCaseDTO)
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
        doNothing().`when`(testCaseService).removeTestCase("1")

        val response = testCaseController.removeTestCase("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Unit, response.body)
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

    @Test
    fun `test 007 - should run a test case`() {
        given(testCaseService.runTestCase("1", "token")).willReturn(testCaseResultDTO)

        val response = testCaseController.runTestCase("1", jwt)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(true, response.body?.hasPassed)
    }

    @Test
    fun `test 008 - should remove a test case`() {
        doNothing().`when`(testCaseService).removeTestCase("1")

        val response = testCaseController.removeTestCase("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(Unit, response.body)
    }
}
