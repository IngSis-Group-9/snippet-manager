package com.ingsis.snippetmanager.controller.snippet

import com.ingsis.snippetmanager.controller.user.UserTO
import org.jetbrains.annotations.NotNull

class SnippetTO(
    @NotNull
    private val name: String,
    @NotNull
    private val type: String,
    @NotNull
    private val content: String,
    @NotNull
    private val owner: UserTO,
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

    fun getOwner(): UserTO {
        return owner
    }
}
