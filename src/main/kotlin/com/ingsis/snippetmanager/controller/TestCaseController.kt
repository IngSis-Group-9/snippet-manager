package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.dto.TestCaseDTO
import com.ingsis.snippetmanager.model.de.TestCase
import com.ingsis.snippetmanager.service.TestCaseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
@RequestMapping("/testCases")
class TestCaseController(private val testCaseService: TestCaseService) {
    @PostMapping("/create")
    fun createTestCase(
        @RequestBody testCaseDTO: TestCaseDTO,
    ): TestCase {
        val newTestCase = testCaseService.createTestCase(testCaseDTO)
        return newTestCase
    }

    @GetMapping
    fun getTestCasesBySnippetId(
        @RequestParam snippetId: Long,
    ): ResponseEntity<List<TestCase>> {
        val testCases = testCaseService.getTestCasesBySnippetId(snippetId)
        return ResponseEntity.ok(testCases)
    }
}
