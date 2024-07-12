package com.ingsis.snippetmanager.dto

import org.jetbrains.annotations.NotNull

class TestCaseDTO(
    @NotNull
    private val name: String,
    @NotNull
    private val input: List<String> = ArrayList(),
    @NotNull
    private val output: List<String> = emptyList(),
    @NotNull
    private val envVars: String,
    @NotNull
    private val snippetId: String,
) {
    fun getName(): String {
        return name
    }

    fun getInput(): List<String> {
        return input
    }

    fun getOutput(): List<String> {
        return output
    }

    fun getEnvVars(): String {
        return envVars
    }

    fun getSnippetId(): String {
        return snippetId
    }
}
