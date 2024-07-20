package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.UpdateUserRuleDTO
import com.ingsis.snippetmanager.model.dto.UserRuleDTO
import com.ingsis.snippetmanager.model.enums.RuleType
import com.ingsis.snippetmanager.service.UserRuleService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
@RequestMapping("/rules")
class RuleController(
    private val userRuleService: UserRuleService,
) {
    @GetMapping
    fun getRules(
        @RequestParam ruleType: RuleType,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<List<UserRuleDTO>> = ResponseEntity.ok(userRuleService.getUserRulesByType(jwt.subject, ruleType))

    @PostMapping
    suspend fun updateUserRules(
        @RequestBody userRules: List<UpdateUserRuleDTO>,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<List<UserRuleDTO>> = ResponseEntity.ok(userRuleService.updateUserRule(jwt.subject, userRules))
}
