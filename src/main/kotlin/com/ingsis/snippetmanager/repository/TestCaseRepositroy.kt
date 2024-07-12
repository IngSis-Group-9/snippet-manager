package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.de.Snippet
import com.ingsis.snippetmanager.model.de.TestCase
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface TestCaseRepositroy : JpaRepository<TestCase, Long> {
    override fun findById(id: Long): Optional<TestCase>

    fun findAllBySnippet(snippet: Snippet): List<TestCase>
}
