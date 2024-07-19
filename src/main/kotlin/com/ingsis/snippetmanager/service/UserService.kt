package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.NotFoundException
import com.ingsis.snippetmanager.model.entity.User
import com.ingsis.snippetmanager.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(UserService::class.java)

    fun registerUser(
        auth0Id: String,
        name: String,
        email: String,
    ): User {
        log.info("Registering user with auth0Id: $auth0Id")
        val existingUser = userRepository.findById(auth0Id)

        if (existingUser.isPresent) {
            return existingUser.get()
        }

        val newUser = User(id = auth0Id, name = name, email = email)
        userRepository.save(newUser)

        return newUser
    }

    fun findUserById(id: String): User {
        log.info("Finding user with id: $id")
        return userRepository.findById(id).orElseThrow {
            log.error("User not found with id: $id")
            NotFoundException("User not found with id: $id")
        }
    }

    fun findFriends(
        name: String,
        userId: String,
    ): List<User>? = userRepository.findByName(name, userId)
}
