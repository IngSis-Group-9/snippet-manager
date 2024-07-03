package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.de.Snippet
import com.ingsis.snippetmanager.model.de.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface SnippetRepository : JpaRepository<Snippet, Long> {
    override fun findById(id: Long): Optional<Snippet>

    @Query("SELECT s FROM Snippet s WHERE (s.owner = :user OR :user MEMBER OF s.sharedWith) AND (s.name LIKE %:snippetName%)")
    fun findAllByOwnerOrSharedWith(
        user: User,
        snippetName: String,
    ): List<Snippet>
}
