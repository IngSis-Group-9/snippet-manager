package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.model.bo.UserBO
import com.ingsis.snippetmanager.model.de.UserDE
import com.ingsis.snippetmanager.model.mapper.UserMapperModel
import com.ingsis.snippetmanager.repository.UserRepository

class UserService(private val userRepository: UserRepository) {
    fun createUser(username: String): UserBO {
        return UserMapperModel().convertUserDEToBo(userRepository.save(UserDE(username)))
    }

    fun getUser(username: String): UserBO {
        return UserMapperModel().convertUserDEToBo(userRepository.findByUsername(username))
    }
}
