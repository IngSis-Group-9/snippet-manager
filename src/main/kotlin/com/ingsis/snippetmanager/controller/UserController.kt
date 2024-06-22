package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.de.User
import com.ingsis.snippetmanager.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/register")
    fun register(@RequestBody auth0Id: String): ResponseEntity<User> {
        val newUser = userService.registerUser(auth0Id)
        return ResponseEntity.ok(newUser)
    }

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: String): ResponseEntity<User> {
        val user = userService.findUserById(id)
        return if (user.isPresent) {
            ResponseEntity.ok(user.get())
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}