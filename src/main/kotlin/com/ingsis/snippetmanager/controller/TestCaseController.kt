package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.CreateTestCaseDTO
import com.ingsis.snippetmanager.model.dto.TestCaseDTO
import com.ingsis.snippetmanager.model.dto.TestCaseResultDTO
import com.ingsis.snippetmanager.model.dto.UpdateTestCaseDTO
import com.ingsis.snippetmanager.service.TestCaseService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController
@RequestMapping("/testCases")
class TestCaseController(
    private val testCaseService: TestCaseService,
) {
    @PostMapping
    fun createTestCase(
        @RequestBody testCaseDTO: CreateTestCaseDTO,
    ): ResponseEntity<TestCaseDTO> = ResponseEntity.ok(testCaseService.createTestCase(testCaseDTO))

    @GetMapping
    fun getTestCasesBySnippetId(
        @RequestParam snippetId: String,
    ): ResponseEntity<List<TestCaseDTO>> = ResponseEntity.ok(testCaseService.getTestCasesBySnippetId(snippetId))

    @PutMapping
    fun updateTestCase(
        @RequestBody testCaseDTO: UpdateTestCaseDTO,
    ): ResponseEntity<TestCaseDTO> = ResponseEntity.ok(testCaseService.updateTestCase(testCaseDTO))

    @PostMapping("/run/{id}")
    fun runTestCase(
        @PathVariable id: String,
        @AuthenticationPrincipal jwt: Jwt,
    ): ResponseEntity<TestCaseResultDTO> = ResponseEntity.ok(testCaseService.runTestCase(id, jwt.tokenValue))

    @DeleteMapping("/{id}")
    fun removeTestCase(
        @PathVariable id: String,
    ): ResponseEntity<Unit> = ResponseEntity.ok(testCaseService.removeTestCase(id))
}
