package com.ingsis.snippetmanager.model.dto

import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import org.jetbrains.annotations.NotNull

data class SnippetDTO(
    val id: String,
    val name: String,
    val content: String,
    val language: String,
    val extension: String,
    val author: String,
    val compliance: ComplianceEnum,
)

data class CreateSnippetDTO(
    @NotNull
    val name: String,
    @NotNull
    val content: String,
    @NotNull
    val language: String,
    @NotNull
    val extension: String,
)

data class UpdateSnippetDTO(
    val content: String
)

data class SnippetContentDTO(
    val content: String,
)
