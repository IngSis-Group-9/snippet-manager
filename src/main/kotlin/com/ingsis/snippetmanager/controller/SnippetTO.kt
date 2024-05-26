package com.ingsis.snippetmanager.controller

import org.jetbrains.annotations.NotNull

class SnippetTO(
    private val id: Long,
    @NotNull
    private val name: String,
    @NotNull
    private val type: String,
    @NotNull
    private val content: String,
)
