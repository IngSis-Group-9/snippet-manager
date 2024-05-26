package com.ingsis.snippetmanager.model.bo

data class SnippetBO(
    private val name: String,
    private val type: String,
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
