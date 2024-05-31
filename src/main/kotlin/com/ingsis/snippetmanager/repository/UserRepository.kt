package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.de.UserDE
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserDE, Long> {
    fun findByUsername(username: String): UserDE
}
