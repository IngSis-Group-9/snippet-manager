package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.CreateSnippetRequest
import com.ingsis.snippetmanager.model.dto.ShareSnippetRequest
import com.ingsis.snippetmanager.model.dto.SnippetDto
import com.ingsis.snippetmanager.model.dto.UpdateSnippetRequest
import com.ingsis.snippetmanager.service.SnippetService
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
@RequestMapping("/snippet-manager/snippets")
class SnippetController(
    private val snippetService: SnippetService,
) {
    @PostMapping
    fun createSnippet(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody request: CreateSnippetRequest,
    ): ResponseEntity<SnippetDto> = ResponseEntity.ok(snippetService.saveSnippet(request, jwt.subject))

    @GetMapping
    fun getAllSnippets(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestParam snippetName: String,
    ): ResponseEntity<List<SnippetDto>> = ResponseEntity.ok(snippetService.getAllSnippets(jwt.subject, snippetName))

    @GetMapping("/{id}")
    fun getSnippet(
        @PathVariable id: String,
    ): ResponseEntity<SnippetDto> = ResponseEntity.ok(snippetService.getSnippet(id))

    @PutMapping("/{id}")
    fun updateSnippet(
        @PathVariable id: String,
        @RequestBody request: UpdateSnippetRequest,
    ): ResponseEntity<SnippetDto> = ResponseEntity.ok(snippetService.updateSnippet(request, id))

    @PostMapping("/{id}/share")
    fun shareSnippet(
        @PathVariable id: String,
        @RequestBody request: ShareSnippetRequest,
    ): ResponseEntity<SnippetDto> = ResponseEntity.ok(snippetService.shareSnippet(request, id))

    @DeleteMapping("/{id}")
    fun deleteSnippet(
        @PathVariable id: String,
    ): ResponseEntity<Unit> = ResponseEntity.ok(snippetService.deleteSnippet(id))
}
