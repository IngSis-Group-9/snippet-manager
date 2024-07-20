package com.ingsis.snippetmanager.model.dto

data class TestCaseDTO(
    val id: String,
    val name: String,
    val input: List<String>,
    val output: List<String>,
    val envVars: String
)

data class CreateTestCaseDTO(
    val name: String,
    val input: List<String>,
    val output: List<String>,
    val envVars: String = "",
    val snippetId: String,
)

data class UpdateTestCaseDTO(
    val name: String,
    val input: List<String> = emptyList(),
    val output: List<String> = emptyList(),
    val envVars: String,
    val id: String,
)

data class TestCaseResultDTO(
    val hasPassed: Boolean,
)

data class RunTestDTO(
    val name: String,
    val input: List<String> = emptyList(),
    val output: List<String> = emptyList(),
    val envVars: String = "",
    val id: String
)
