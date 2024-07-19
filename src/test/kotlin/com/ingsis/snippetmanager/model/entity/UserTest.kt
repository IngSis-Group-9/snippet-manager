package com.ingsis.snippetmanager.model.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserTest {
    @Test
    fun testUser() {
        val user =
            User(
                id = "1",
                name = "testuser",
                email = "testuser@example.com",
            )
        assertEquals("1", user.id)
        assertEquals("testuser", user.name)
        assertEquals("testuser@example.com", user.email)
    }
}
