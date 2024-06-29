package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.de.SnippetDE
import com.ingsis.snippetmanager.model.de.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface SnippetRepository : JpaRepository<SnippetDE, Long> {
    override fun findById(id: Long): Optional<SnippetDE>

    @Query("SELECT s FROM SnippetDE s WHERE s.owner = :user OR :user MEMBER OF s.sharedWith")
    fun findAllByOwnerOrSharedWith(user: User): List<SnippetDE>
}
