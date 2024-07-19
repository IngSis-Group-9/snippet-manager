package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.ServiceUnavailableException
import com.ingsis.snippetmanager.model.dto.FormatterRulesDTO
import com.ingsis.snippetmanager.model.dto.SnippetContentDTO
import com.ingsis.snippetmanager.model.dto.UserRuleDTO
import com.ingsis.snippetmanager.model.enums.RuleType
import com.ingsis.snippetmanager.model.enums.RuleValueType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class RunnerServiceTest {

    @Mock
    private lateinit var processingService: CodeProcessingService

    @Mock
    private lateinit var userRuleService: UserRuleService

    @InjectMocks
    private lateinit var runnerService: RunnerService

    @Test
    fun `test 001 - formatSnippet should return formatted content when response is successful`() {
        val snippetContentDTO = SnippetContentDTO(content = "original snippet content")
        val userId = "user1"
        val token = "token123"
        val formatterRules = FormatterRulesDTO(
            spaceBeforeColon = true,
            spaceAfterColon = false,
            spaceAroundAssignment = true,
            newlineBeforePrintln = 2,
            nSpacesIndentationForIfStatement = 4
        )
        val formattedContent = "formatted snippet content"
        val response = ResponseEntity(formattedContent, HttpStatus.OK)

        // Setup mock behavior
        `when`(userRuleService.getUserRulesByType(userId, RuleType.FORMATTER)).thenReturn(
            listOf(
                UserRuleDTO(
                    id = "1",
                    userId = userId,
                    name = "spaceBeforeColon",
                    valueType = RuleValueType.BOOLEAN,
                    ruleType = RuleType.FORMATTER,
                    value = "true",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "2",
                    userId = userId,
                    name = "spaceAfterColon",
                    valueType = RuleValueType.BOOLEAN,
                    ruleType = RuleType.FORMATTER,
                    value = "false",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "3",
                    userId = userId,
                    name = "spaceAroundAssignment",
                    valueType = RuleValueType.BOOLEAN,
                    ruleType = RuleType.FORMATTER,
                    value = "true",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "4",
                    userId = userId,
                    name = "newlineBeforePrintln",
                    valueType = RuleValueType.INTEGER,
                    ruleType = RuleType.FORMATTER,
                    value = "2",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "5",
                    userId = userId,
                    name = "nSpacesIndentationForIfStatement",
                    valueType = RuleValueType.INTEGER,
                    ruleType = RuleType.FORMATTER,
                    value = "4",
                    isActive = true
                )
            )
        )
        `when`(processingService.formatSnippet(
            snippetContentDTO.content,
            formatterRules,
            token
        )).thenReturn(response)

        val result = runnerService.formatSnippet(snippetContentDTO, userId, token)

        assertEquals(formattedContent, result)
    }

    @Test
    fun `test 002 - formatSnippet should throw ServiceUnavailableException when response is not successful`() {
        val snippetContentDTO = SnippetContentDTO(content = "original snippet content")
        val userId = "user1"
        val token = "token123"
        val formatterRules = FormatterRulesDTO(
            spaceBeforeColon = true,
            spaceAfterColon = false,
            spaceAroundAssignment = true,
            newlineBeforePrintln = 2,
            nSpacesIndentationForIfStatement = 4
        )
        val response = ResponseEntity("error", HttpStatus.INTERNAL_SERVER_ERROR)

        // Setup mock behavior
        `when`(userRuleService.getUserRulesByType(userId, RuleType.FORMATTER)).thenReturn(
            listOf(
                UserRuleDTO(
                    id = "1",
                    userId = userId,
                    name = "spaceBeforeColon",
                    valueType = RuleValueType.BOOLEAN,
                    ruleType = RuleType.FORMATTER,
                    value = "true",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "2",
                    userId = userId,
                    name = "spaceAfterColon",
                    valueType = RuleValueType.BOOLEAN,
                    ruleType = RuleType.FORMATTER,
                    value = "false",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "3",
                    userId = userId,
                    name = "spaceAroundAssignment",
                    valueType = RuleValueType.BOOLEAN,
                    ruleType = RuleType.FORMATTER,
                    value = "true",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "4",
                    userId = userId,
                    name = "newlineBeforePrintln",
                    valueType = RuleValueType.INTEGER,
                    ruleType = RuleType.FORMATTER,
                    value = "2",
                    isActive = true
                ),
                UserRuleDTO(
                    id = "5",
                    userId = userId,
                    name = "nSpacesIndentationForIfStatement",
                    valueType = RuleValueType.INTEGER,
                    ruleType = RuleType.FORMATTER,
                    value = "4",
                    isActive = true
                )
            )
        )

        `when`(processingService.formatSnippet(
            snippetContentDTO.content,
            formatterRules,
            token
        )).thenReturn(response)

        assertThrows(ServiceUnavailableException::class.java) {
            runnerService.formatSnippet(snippetContentDTO, userId, token)
        }
    }
}