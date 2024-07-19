package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.CreateSnippetDTO
import com.ingsis.snippetmanager.model.dto.SnippetDTO
import com.ingsis.snippetmanager.model.dto.UpdateSnippetDTO
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
@RequestMapping("/snippets")
class SnippetController(
    private val snippetService: SnippetService,
) {
    @PostMapping
    fun createSnippet(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody createSnippetDTO: CreateSnippetDTO,
    ): ResponseEntity<SnippetDTO> = ResponseEntity.ok(snippetService.saveSnippet(createSnippetDTO, jwt.subject))

    @GetMapping
    fun getAllSnippets(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestParam snippetName: String,
    ): ResponseEntity<List<SnippetDTO>> = ResponseEntity.ok(snippetService.getAllSnippets(jwt.subject, snippetName))

    @GetMapping("/{id}")
    fun getSnippet(
        @PathVariable id: String,
    ): ResponseEntity<SnippetDTO> = ResponseEntity.ok(snippetService.getSnippet(id))

    @PutMapping
    fun updateSnippet(
        @RequestBody updateSnippetDTO: UpdateSnippetDTO,
    ): ResponseEntity<SnippetDTO> = ResponseEntity.ok(snippetService.updateSnippet(updateSnippetDTO))

    @PostMapping("/{id}/share/{shareId}")
    fun shareSnippet(
        @PathVariable id: String,
        @PathVariable shareId: String,
    ): ResponseEntity<SnippetDTO> = ResponseEntity.ok(snippetService.shareSnippet(shareId, id))

    @DeleteMapping("/{id}")
    fun deleteSnippet(
        @PathVariable id: String,
    ): ResponseEntity<Unit> = ResponseEntity.ok(snippetService.deleteSnippet(id))
}
