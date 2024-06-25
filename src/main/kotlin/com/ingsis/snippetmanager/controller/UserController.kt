package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.de.User
import com.ingsis.snippetmanager.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @PostMapping("/register")
    fun register(
        @RequestBody userRegister: UserRegister,
    ): ResponseEntity<User> {
        val newUser = userService.registerUser(userRegister.getId(), userRegister.getName(), userRegister.getEmail())
        return ResponseEntity.ok(newUser)
    }

    @GetMapping("/{id}")
    fun findUserById(
        @PathVariable id: String,
    ): ResponseEntity<User> {
        val user = userService.findUserById(id)
        return if (user.isPresent) {
            ResponseEntity.ok(user.get())
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}
