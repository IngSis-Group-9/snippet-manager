package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.model.de.User
import com.ingsis.snippetmanager.repository.UserRepository
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class UserService(private val userRepository: UserRepository) {
    fun registerUser(
        auth0Id: String,
        name: String,
        email: String,
    ): User {
        val existingUser = userRepository.findById(auth0Id)

        if (existingUser.isPresent) {
            return existingUser.get()
        }

        val newUser = User(id = auth0Id, name = name, email = email)
        userRepository.save(newUser)

        return newUser
    }

    fun findUserById(id: String): Optional<User> {
        return userRepository.findById(id)
    }

    fun createUser(user: User): Optional<User> {
        return Optional.of(userRepository.save(user))
    }

    fun findFriends(
        name: String,
        userId: String,
    ): List<User>? {
        return userRepository.findByName(name, userId)
    }
}
