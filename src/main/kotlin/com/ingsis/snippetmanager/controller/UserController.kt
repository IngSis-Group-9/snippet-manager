package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.entity.User
import com.ingsis.snippetmanager.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/register")
    fun register(
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<User> {
        val userId = jwt.subject
        val name = jwt.getClaimAsString("https://ingisis-group-9/name")
        val email = jwt.getClaimAsString("https://ingisis-group-9/email")
        val newUser = userService.registerUser(userId, name, email)
        return ResponseEntity.ok(newUser)
    }

    @GetMapping("/{id}")
    fun findUserById(
        @PathVariable id: String,
    ): ResponseEntity<User> = ResponseEntity.ok(userService.findUserById(id))

    @GetMapping("/friends")
    fun findFriends(
        @RequestParam name: String,
        @RequestParam userId: String,
    ): ResponseEntity<List<User>> {
        val friends = userService.findFriends(name, userId)
        return ResponseEntity.ok(friends)
    }
}
