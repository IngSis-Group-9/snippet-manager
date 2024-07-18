package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.NotFoundException
import com.ingsis.snippetmanager.model.dto.TestCaseDTO
import com.ingsis.snippetmanager.model.entity.TestCase
import com.ingsis.snippetmanager.repository.SnippetRepository
import com.ingsis.snippetmanager.repository.TestCaseRepository
import org.springframework.stereotype.Service

@Service
class TestCaseService(
    private val testCaseRepository: TestCaseRepository,
    private val snippetRepository: SnippetRepository,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(SnippetService::class.java)

    fun createTestCase(testCaseDTO: TestCaseDTO): TestCase {
        log.info("Creating test case: { name: ${testCaseDTO.name}, snippetId: ${testCaseDTO.snippetId} }")
        val snippet =
            snippetRepository.findById(testCaseDTO.snippetId).orElseThrow {
                log.error("Snippet with id: ${testCaseDTO.snippetId} not found")
                throw NotFoundException("Snippet with id: ${testCaseDTO.snippetId} not found")
            }

        val testCase =
            TestCase(
                name = testCaseDTO.name,
                input = testCaseDTO.input,
                output = testCaseDTO.output,
                envVars = parseStringToMap(testCaseDTO.envVars),
                snippet = snippet,
            )

        val savedTestCase = testCaseRepository.save(testCase)
        return savedTestCase
    }

    fun getTestCasesBySnippetId(snippetId: String): List<TestCase> {
        val snippet =
            snippetRepository.findById(snippetId).orElseThrow {
                log.error("Snippet with id: $snippetId not found")
                NotFoundException("Snippet with id: $snippetId not found")
            }
        return testCaseRepository.findAllBySnippet(snippet)
    }

    fun removeTestCase(id: String): String {
        val testCase =
            testCaseRepository.findById(id).orElseThrow {
                log.error("Test case with id: $id not found")
                NotFoundException("Test case with id: $id not found")
            }
        testCaseRepository.delete(testCase)
        return "Test case deleted successfully"
    }

    private fun parseStringToMap(envVars: String): Map<String, String> {
        val map = mutableMapOf<String, String>()
        envVars.split(",").forEach {
            val pair = it.split(":")
            map[pair[0].trim()] = pair[1].trim()
        }
        return map
    }
}
