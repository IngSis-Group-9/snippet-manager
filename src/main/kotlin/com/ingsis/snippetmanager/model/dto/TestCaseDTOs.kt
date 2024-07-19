package com.ingsis.snippetmanager.model.dto

data class TestCaseDTO(
    val id: String,
    val name: String,
    val inputs: List<String>,
    val outputs: List<String>,
    val envVars: String,
    val snippetId: String,
)

data class CreateTestCaseDTO(
    val name: String,
    val input: List<String> = ArrayList(),
    val output: List<String> = emptyList(),
    val envVars: String,
    val snippetId: String,
)

data class UpdateTestCaseDTO(
    val name: String,
    val input: List<String> = ArrayList(),
    val output: List<String> = emptyList(),
    val envVars: String,
    val id: String,
)

data class TestCaseResultDTO(
    val hasPassed: Boolean,
)
