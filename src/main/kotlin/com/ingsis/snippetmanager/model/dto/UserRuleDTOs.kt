package com.ingsis.snippetmanager.model.dto

import com.ingsis.snippetmanager.model.enums.RuleType
import com.ingsis.snippetmanager.model.enums.RuleValueType

data class UserRuleDTO(
    val id: String,
    val userId: String,
    val name: String,
    val valueType: RuleValueType,
    val ruleType: RuleType,
    val value: String,
    val isActive: Boolean,
)

data class UpdateUserRuleDTO(
    val id: String,
    val value: String,
    val isActive: Boolean,
)

data class LinterRulesDTO(
    var printlnArgumentCheck: Boolean = false,
    var typeMatchingCheck: Boolean = false,
    var identifierNamingCheck: Boolean = false,
)

data class FormatterRulesDTO(
    var spaceBeforeColon: Boolean = false,
    var spaceAfterColon: Boolean = false,
    var spaceAroundAssignment: Boolean = false,
    var newlineBeforePrintln: Int = 1,
    var nSpacesIndentationForIfStatement: Int = 1,
)
