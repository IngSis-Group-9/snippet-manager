package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.de.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface UserRepository : JpaRepository<User, String> {
    override fun findById(id: String): Optional<User>

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name% AND u.id != :userId")
    fun findByName(
        name: String,
        userId: String,
    ): List<User>?
}
