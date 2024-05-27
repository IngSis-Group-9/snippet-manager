package com.ingsis.snippetmanager.controller

import org.jetbrains.annotations.NotNull

class SnippetTO(
    @NotNull
    private val name: String,
    @NotNull
    private val type: String,
    @NotNull
    private val content: String,
) {
    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getContent(): String {
        return content
    }
}
