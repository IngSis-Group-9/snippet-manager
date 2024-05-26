package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.de.SnippetDE
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SnippetRepository : JpaRepository<SnippetDE, Long> {
    override fun findById(id: Long): Optional<SnippetDE>
}
