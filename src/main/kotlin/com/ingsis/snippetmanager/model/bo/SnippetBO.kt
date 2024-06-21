package com.ingsis.snippetmanager.model.bo

data class SnippetBO(
    private val id: Long,
    private val name: String,
    private val content: String,
    private val language: String,
    private val extension: String
) {
    fun getId(): Long {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getContent(): String {
        return content
    }

    fun getLanguage(): String {
        return language
    }

    fun getExtension(): String {
        return extension
    }
}
