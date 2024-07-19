package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.entity.Snippet
import com.ingsis.snippetmanager.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface SnippetRepository : JpaRepository<Snippet, String> {
    @Query("SELECT s FROM Snippet s WHERE (s.owner = :user OR :user MEMBER OF s.sharedWith) AND (s.name LIKE %:snippetName%)")
    fun findAllByOwnerOrSharedWith(
        user: User,
        snippetName: String,
    ): List<Snippet>

    fun findAllByOwnerId(ownerId: String): List<Snippet>

    fun findByIdAndOwnerId(
        id: String,
        ownerId: String,
    ): Optional<Snippet>
}
