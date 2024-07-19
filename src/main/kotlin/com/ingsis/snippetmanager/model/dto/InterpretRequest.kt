package com.ingsis.snippetmanager.model.dto

data class InterpretRequest(
    val snippet: String,
    val inputs: List<String>,
    val envs: Map<String, String>,
)
