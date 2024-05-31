package com.ingsis.snippetmanager.model.bo

class UserBO(
    private val username: String,
) {
    fun getUsername(): String {
        return username
    }
}
