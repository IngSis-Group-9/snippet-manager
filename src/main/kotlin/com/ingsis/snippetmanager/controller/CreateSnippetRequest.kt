package com.ingsis.snippetmanager.controller

import org.jetbrains.annotations.NotNull

class CreateSnippetRequest(
    @NotNull
    private val id: Long,
    @NotNull
    private val name: String,
    @NotNull
    private val content: String,
    @NotNull
    private val language: String,
    @NotNull
    private val extension: String,
    @NotNull
    private val ownerId: String,
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

    fun getOwnerId(): String {
        return ownerId
    }
}
