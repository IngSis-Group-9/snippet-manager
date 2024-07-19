package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.UserRuleDTO
import com.ingsis.snippetmanager.model.enums.RuleType
import com.ingsis.snippetmanager.model.enums.RuleValueType
import com.ingsis.snippetmanager.service.UserRuleService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.oauth2.jwt.Jwt

@ExtendWith(MockitoExtension::class)
class RuleControllerTest {
    @Mock
    private lateinit var userRuleService: UserRuleService

    @InjectMocks
    private lateinit var ruleController: RuleController

    @Test
    fun `test 001 - should get rules`() {
        val ruleType = RuleType.FORMATTER
        val jwt: Jwt =
            Jwt
                .withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user-id")
                .build()

        val userRuleDTO =
            UserRuleDTO(
                id = "1",
                userId = "user-id",
                name = "Rule Name",
                valueType = RuleValueType.BOOLEAN,
                ruleType = ruleType,
                value = "Rule Value",
                isActive = true,
            )

        given(userRuleService.getUserRulesByType(jwt.subject, ruleType))
            .willReturn(listOf(userRuleDTO))

        val response: ResponseEntity<List<UserRuleDTO>> = ruleController.getRules(ruleType, jwt)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(listOf(userRuleDTO), response.body)
    }

    @Test
    fun `test 002 - should handle error when getting rules`() {
        val ruleType = RuleType.FORMATTER
        val jwt: Jwt =
            Jwt
                .withTokenValue("token")
                .header("alg", "none")
                .claim("sub", "user-id")
                .build()

        given(userRuleService.getUserRulesByType(jwt.subject, ruleType))
            .willThrow(RuntimeException("Error fetching rules"))

        val exception =
            org.junit.jupiter.api.assertThrows<RuntimeException> {
                ruleController.getRules(ruleType, jwt)
            }

        assertEquals("Error fetching rules", exception.message)
    }
}
