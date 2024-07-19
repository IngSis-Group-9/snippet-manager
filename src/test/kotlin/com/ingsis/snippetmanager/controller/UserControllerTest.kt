package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.entity.User
import com.ingsis.snippetmanager.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.security.oauth2.jwt.Jwt

@ExtendWith(MockitoExtension::class)
class UserControllerTest {
    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var userController: UserController

    @Test
    fun `test 001 - should register a user`() {
        val jwt = mock(Jwt::class.java)
        given(jwt.subject).willReturn("1")
        given(jwt.getClaimAsString("https://ingisis-group-9/name")).willReturn("Test User")
        given(jwt.getClaimAsString("https://ingisis-group-9/email")).willReturn("testuser@example.com")

        val user =
            User(
                id = "1",
                name = "Test User",
                email = "testuser@example.com",
            )
        given(userService.registerUser("1", "Test User", "testuser@example.com")).willReturn(user)

        val response = userController.register(jwt)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
    }

    @Test
    fun `test 002 - should find a user by id if exists`() {
        val user =
            User(
                id = "1",
                name = "Test User",
                email = "test@example.com",
            )
        given(userService.findUserById("1")).willReturn(user)

        val response = userController.findUserById("1")

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(user, response.body)
    }

    @Test
    fun `test 003 - should find friends by name`() {
        val userId = "1"
        val friends =
            listOf(
                User(id = "2", name = "Friend 1", email = "friend1@example.com"),
                User(id = "3", name = "Friend 2", email = "friend2@example.com"),
            )

        given(userService.findFriends("Test User", userId)).willReturn(friends)

        val response = userController.findFriends("Test User", userId)

        assertNotNull(response)
        assertEquals(HttpStatus.OK, response.statusCode)
    }
}
