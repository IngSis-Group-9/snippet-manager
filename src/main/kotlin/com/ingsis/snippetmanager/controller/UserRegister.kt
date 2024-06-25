package com.ingsis.snippetmanager.controller

import org.jetbrains.annotations.NotNull

class UserRegister(
    @NotNull
    private val id: String,
    @NotNull
    private val name: String,
    @NotNull
    private val email: String,
) {
    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getEmail(): String {
        return email
    }
}
