package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.dto.TestCaseDTO
import com.ingsis.snippetmanager.model.de.TestCase
import com.ingsis.snippetmanager.repository.SnippetRepository
import com.ingsis.snippetmanager.repository.TestCaseRepositroy
import org.springframework.stereotype.Service

@Service
class TestCaseService(
    private val testCaseRepositroy: TestCaseRepositroy,
    private val snippetRepository: SnippetRepository,
) {
    fun createTestCase(testCaseDTO: TestCaseDTO): TestCase {
        val snippet = snippetRepository.findById(testCaseDTO.getSnippetId().toLong()).orElseThrow { Exception("Snippet not found") }

        val testCase =
            TestCase(
                name = testCaseDTO.getName(),
                input = testCaseDTO.getInput(),
                output = testCaseDTO.getOutput(),
                envVars = parseStringToList(testCaseDTO.getEnvVars()),
                snippet = snippet,
            )

        val savedTestCase = testCaseRepositroy.save(testCase)
        return savedTestCase
    }

    private fun parseStringToList(string: String): List<String> {
        return string.substring(0, string.length - 1).split(",").map { it.trim() }
    }
}
