package com.ingsis.snippetmanager.model.dto

data class FormatterRequest(
    val snippet: String,
    val formatterRules: FormatterRulesDTO,
)
