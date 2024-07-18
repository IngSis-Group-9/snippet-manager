package com.ingsis.snippetmanager.model.dto

import org.jetbrains.annotations.NotNull

data class CreateSnippetRequest(
    @NotNull
    val name: String,
    @NotNull
    val content: String,
    @NotNull
    val language: String,
    @NotNull
    val extension: String,
)
