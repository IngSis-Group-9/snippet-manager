package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.ServiceUnavailableException
import com.ingsis.snippetmanager.model.dto.FormatterRulesDTO
import com.ingsis.snippetmanager.model.dto.SnippetContentDTO
import com.ingsis.snippetmanager.model.enums.RuleType
import org.springframework.stereotype.Service

@Service
class RunnerService(
    private val processingService: CodeProcessingService,
    private val userRuleService: UserRuleService,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(RunnerService::class.java)

    fun formatSnippet(
        snippetContentDTO: SnippetContentDTO,
        userId: String,
        token: String,
    ): String {
        log.info("Formatting snippet: ${snippetContentDTO.content}")
        val formatterRules = FormatterRulesDTO()
        userRuleService.getUserRulesByType(userId, RuleType.FORMATTER).forEach {
            when (it.name) {
                "spaceBeforeColon" -> formatterRules.spaceBeforeColon = it.value.toBoolean()
                "spaceAfterColon" -> formatterRules.spaceAfterColon = it.value.toBoolean()
                "spaceAroundAssignment" -> formatterRules.spaceAroundAssignment = it.value.toBoolean()
                "newlineBeforePrintln" -> formatterRules.newlineBeforePrintln = it.value.toInt()
                "nSpacesIndentationForIfStatement" -> formatterRules.nSpacesIndentationForIfStatement = it.value.toInt()
            }
        }

        val formatterResponse =
            processingService.formatSnippet(
                snippetContentDTO.content,
                formatterRules,
                token,
            )

        if (!formatterResponse.statusCode.is2xxSuccessful) {
            log.error("Error formatting snippet content ${snippetContentDTO.content}")
            throw ServiceUnavailableException("Error formatting snippet content")
        }

        return formatterResponse.body!!
    }
}
