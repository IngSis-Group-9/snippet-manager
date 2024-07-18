package com.ingsis.snippetmanager.model.dto

import org.jetbrains.annotations.NotNull

data class TestCaseDTO(
    @NotNull
    val name: String,
    @NotNull
    val input: List<String> = ArrayList(),
    @NotNull
    val output: List<String> = emptyList(),
    @NotNull
    val envVars: String,
    @NotNull
    val snippetId: String,
)
