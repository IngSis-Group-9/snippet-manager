package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.NotFoundException
import com.ingsis.snippetmanager.model.dto.CreateTestCaseDTO
import com.ingsis.snippetmanager.model.dto.TestCaseDTO
import com.ingsis.snippetmanager.model.dto.TestCaseResultDTO
import com.ingsis.snippetmanager.model.dto.UpdateTestCaseDTO
import com.ingsis.snippetmanager.model.entity.TestCase
import com.ingsis.snippetmanager.repository.SnippetRepository
import com.ingsis.snippetmanager.repository.TestCaseRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class TestCaseService(
    private val testCaseRepository: TestCaseRepository,
    private val snippetRepository: SnippetRepository,
    private val processingService: CodeProcessingService,
    private val assetService: AssetService,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(SnippetService::class.java)

    @Transactional
    fun createTestCase(testCaseDTO: CreateTestCaseDTO): TestCaseDTO {
        log.info("Creating test case: { name: ${testCaseDTO.name}, snippetId: ${testCaseDTO.snippetId} }")
        val snippet =
            snippetRepository.findById(testCaseDTO.snippetId).orElseThrow {
                log.error("Snippet with id: ${testCaseDTO.snippetId} not found")
                throw NotFoundException("Snippet with id: ${testCaseDTO.snippetId} not found")
            }

        val testCase =
            TestCase(
                testCaseDTO.name,
                testCaseDTO.input,
                testCaseDTO.output,
                parseStringToMap(testCaseDTO.envVars),
                snippet,
            )

        val savedTestCase = testCaseRepository.save(testCase)
        return TestCaseDTO(
            savedTestCase.id!!,
            savedTestCase.name,
            savedTestCase.input,
            savedTestCase.output,
            savedTestCase.envVars.map { entry -> "${entry.key}:${entry.value}" }.joinToString(","),
            savedTestCase.snippet.id!!,
        )
    }

    fun getTestCasesBySnippetId(snippetId: String): List<TestCaseDTO> {
        val snippet =
            snippetRepository.findById(snippetId).orElseThrow {
                log.error("Snippet with id: $snippetId not found")
                NotFoundException("Snippet with id: $snippetId not found")
            }
        return testCaseRepository.findAllBySnippet(snippet).map {
            TestCaseDTO(
                it.id!!,
                it.name,
                it.input,
                it.output,
                it.envVars.map { entry -> "${entry.key}:${entry.value}" }.joinToString(","),
                it.snippet.id!!,
            )
        }
    }

    @Transactional
    fun updateTestCase(testCaseDTO: UpdateTestCaseDTO): TestCaseDTO {
        val testCase =
            testCaseRepository.findById(testCaseDTO.id).orElseThrow {
                log.error("Test case with id: ${testCaseDTO.id} not found")
                NotFoundException("Test case with id: ${testCaseDTO.id} not found")
            }
        testCase.name = testCaseDTO.name
        testCase.input = testCaseDTO.input
        testCase.output = testCaseDTO.output
        testCase.envVars = parseStringToMap(testCaseDTO.envVars)

        val updatedTestCase = testCaseRepository.save(testCase)
        return TestCaseDTO(
            updatedTestCase.id!!,
            updatedTestCase.name,
            updatedTestCase.input,
            updatedTestCase.output,
            updatedTestCase.envVars.map { entry -> "${entry.key}:${entry.value}" }.joinToString(","),
            updatedTestCase.snippet.id!!,
        )
    }

    fun runTestCase(
        id: String,
        token: String,
    ): TestCaseResultDTO {
        val testCase =
            testCaseRepository.findById(id).orElseThrow {
                log.error("Test case with id: $id not found")
                NotFoundException("Test case with id: $id not found")
            }

        val snippet = assetService.getSnippet(testCase.snippet.id!!)
        if (!snippet.statusCode.is2xxSuccessful) {
            log.error("Snippet with id: ${testCase.snippet.id} not found")
            throw NotFoundException("Snippet with id: ${testCase.snippet.id} not found")
        }

        val interpretResponse =
            processingService.interpretSnippet(
                snippet.body!!,
                testCase.input,
                testCase.envVars,
                token,
            )

        if (!interpretResponse.statusCode.is2xxSuccessful) {
            log.error("Error running test case with id: $id")
            throw RuntimeException("Error running test case with id: $id")
        }

        if (interpretResponse.body!!.errors.isNotEmpty() || interpretResponse.body!!.outputs != testCase.output) {
            log.info("Test case with id: $id failed")
            return TestCaseResultDTO(false)
        }

        return TestCaseResultDTO(true)
    }

    fun removeTestCase(id: String) {
        val testCase =
            testCaseRepository.findById(id).orElseThrow {
                log.error("Test case with id: $id not found")
                NotFoundException("Test case with id: $id not found")
            }
        testCaseRepository.delete(testCase)
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
