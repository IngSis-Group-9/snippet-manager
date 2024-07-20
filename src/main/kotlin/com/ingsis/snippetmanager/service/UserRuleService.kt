package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.NotFoundException
import com.ingsis.snippetmanager.model.dto.LinterRulesDTO
import com.ingsis.snippetmanager.model.dto.UpdateUserRuleDTO
import com.ingsis.snippetmanager.model.dto.UserRuleDTO
import com.ingsis.snippetmanager.model.entity.Rule
import com.ingsis.snippetmanager.model.entity.Snippet
import com.ingsis.snippetmanager.model.entity.UserRule
import com.ingsis.snippetmanager.model.enums.RuleType
import com.ingsis.snippetmanager.model.enums.RuleValueType
import com.ingsis.snippetmanager.redis.producer.LinterRequest
import com.ingsis.snippetmanager.redis.producer.LinterRequestProducer
import com.ingsis.snippetmanager.repository.UserRuleRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserRuleService(
    private val userService: UserService,
    private val userRuleRepository: UserRuleRepository,
    private val linterRequestProducer: LinterRequestProducer,
    private val snippetService: SnippetService,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(UserRuleService::class.java)

    private val defaultLinterRules =
        listOf(
            Rule("printlnArgumentCheck", RuleType.LINTER, RuleValueType.BOOLEAN, "false"),
            Rule("typeMatchingCheck", RuleType.LINTER, RuleValueType.BOOLEAN, "false"),
            Rule("identifierNamingCheck", RuleType.LINTER, RuleValueType.BOOLEAN, "false"),
        )

    private val defaultFormatterRules =
        listOf(
            Rule("spaceBeforeColon", RuleType.FORMATTER, RuleValueType.BOOLEAN, "false"),
            Rule("spaceAfterColon", RuleType.FORMATTER, RuleValueType.BOOLEAN, "false"),
            Rule("spaceAroundAssignment", RuleType.FORMATTER, RuleValueType.BOOLEAN, "false"),
            Rule("newlineBeforePrintln", RuleType.FORMATTER, RuleValueType.INTEGER, "1"),
            Rule("nSpacesIndentationForIfStatement", RuleType.FORMATTER, RuleValueType.INTEGER, "1"),
        )

    fun getUserRulesByType(
        userId: String,
        ruleType: RuleType,
    ): List<UserRuleDTO> {
        log.info("Getting rules for userId: $userId and ruleType: $ruleType")
        val userRules = userRuleRepository.findAllByOwnerIdAndRuleRuleType(userId, ruleType)
        if (userRules.isEmpty()) {
            log.info("Creating default rules for userId: $userId and ruleType: $ruleType")
            createDefaultRules(userId, ruleType)
            return userRuleRepository.findAllByOwnerIdAndRuleRuleType(userId, ruleType).map {
                UserRuleDTO(
                    it.id!!,
                    it.owner.id,
                    it.rule.name,
                    it.rule.valueType,
                    it.rule.ruleType,
                    it.value,
                    it.isActive,
                )
            }
        } else {
            return userRules.map {
                UserRuleDTO(
                    it.id!!,
                    it.owner.id,
                    it.rule.name,
                    it.rule.valueType,
                    it.rule.ruleType,
                    it.value,
                    it.isActive,
                )
            }
        }
    }

    @Transactional
    suspend fun updateUserRule(
        userId: String,
        userRules: List<UpdateUserRuleDTO>,
    ): List<UserRuleDTO> {
        val updatedRules =
            userRules.map {
                log.info("Updating userRule with id: ${it.id} value: ${it.value} isActive: ${it.isActive} for user: $userId")
                val userRule =
                    userRuleRepository.findById(it.id).orElseThrow {
                        log.error("UserRule not found with id: ${it.id}")
                        NotFoundException("UserRule not found with id: ${it.id}")
                    }
                userRule.value = it.value
                userRule.isActive = it.isActive
                userRule
            }
        userRuleRepository.saveAll(updatedRules)

        if (updatedRules.any { it.rule.ruleType == RuleType.LINTER }) {
            log.info("Linting all snippets for user: $userId")
            val snippets = snippetService.setSnippetsComplianceToPending(userId)
            sendLinterRequest(userId, snippets)
        }

        return updatedRules.map {
            UserRuleDTO(
                it.id!!,
                it.owner.id,
                it.rule.name,
                it.rule.valueType,
                it.rule.ruleType,
                it.value,
                it.isActive,
            )
        }
    }

    private suspend fun sendLinterRequest(
        userId: String,
        snippets: List<Snippet>,
    ) {
        val linterRules = LinterRulesDTO()
        getUserRulesByType(userId, RuleType.LINTER).forEach {
            when (it.name) {
                "printlnArgumentCheck" -> linterRules.printlnArgumentCheck = it.value.toBoolean()
                "typeMatchingCheck" -> linterRules.typeMatchingCheck = it.value.toBoolean()
                "identifierNamingCheck" -> linterRules.identifierNamingCheck = it.value.toBoolean()
            }
        }
        snippets.forEach {
            linterRequestProducer.publishEvent(
                LinterRequest(userId, it.id!!, linterRules),
            )
        }
    }

    private fun createDefaultRules(
        userId: String,
        ruleType: RuleType,
    ) {
        val user = userService.findUserById(userId)
        when (ruleType) {
            RuleType.LINTER -> {
                userRuleRepository.saveAll(
                    defaultLinterRules.map {
                        UserRule(
                            user,
                            it.defaultValue,
                            false,
                            it,
                        )
                    },
                )
            }
            RuleType.FORMATTER -> {
                userRuleRepository.saveAll(
                    defaultFormatterRules.map {
                        UserRule(
                            user,
                            it.defaultValue,
                            false,
                            it,
                        )
                    },
                )
            }
        }
    }
}
