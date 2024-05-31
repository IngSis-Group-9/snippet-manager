package com.ingsis.snippetmanager.controller.user

import org.jetbrains.annotations.NotNull

class UserTO(
    @NotNull
    private val username: String,
) {
    fun getUsername(): String {
        return username
    }
}
