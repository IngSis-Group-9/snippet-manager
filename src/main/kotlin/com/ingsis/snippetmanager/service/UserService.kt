package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.model.de.User
import com.ingsis.snippetmanager.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(private val userRepository: UserRepository) {

    fun registerUser(auth0Id: String): User {
        val existingUser = userRepository.findById(auth0Id)

        if (existingUser.isPresent) {
            return existingUser.get()
        }

        val newUser = User(id = auth0Id)
        userRepository.save(newUser)

        return newUser
    }

    fun findUserById(id: String): Optional<User> {
        return userRepository.findById(id)
    }
}