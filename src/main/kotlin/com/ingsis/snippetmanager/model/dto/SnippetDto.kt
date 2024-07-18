package com.ingsis.snippetmanager.model.dto

import com.ingsis.snippetmanager.model.enums.ComplianceEnum

data class SnippetDto(
    val id: String,
    val name: String,
    val content: String,
    val language: String,
    val extension: String,
    val author: String,
    val compliance: ComplianceEnum,
)
