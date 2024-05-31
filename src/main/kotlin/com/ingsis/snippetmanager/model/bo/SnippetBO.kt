package com.ingsis.snippetmanager.model.bo

data class SnippetBO(
    private val name: String,
    private val type: String,
    private val content: String,
    private val owner: UserBO,
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

    fun getOwner(): UserBO {
        return owner
    }
}
