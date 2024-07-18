package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.entity.Snippet
import com.ingsis.snippetmanager.model.entity.TestCase
import org.springframework.data.jpa.repository.JpaRepository

interface TestCaseRepository : JpaRepository<TestCase, String> {
    fun findAllBySnippet(snippet: Snippet): List<TestCase>
}
