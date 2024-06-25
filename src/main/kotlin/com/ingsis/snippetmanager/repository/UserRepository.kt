package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.de.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, String> {
    override fun findById(id: String): Optional<User>
}
