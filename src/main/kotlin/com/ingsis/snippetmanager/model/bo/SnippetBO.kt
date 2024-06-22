package com.ingsis.snippetmanager.model.bo

import com.ingsis.snippetmanager.model.de.User

data class SnippetBO(
    private val id: Long,
    private val name: String,
    private val content: String,
    private val language: String,
    private val extension: String,
    private val owner: User,
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

    fun getOwner(): User {
        return owner
    }
}
