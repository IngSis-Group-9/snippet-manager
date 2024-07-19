package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.dto.SnippetContentDTO
import com.ingsis.snippetmanager.service.RunnerService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@CrossOrigin("*")
@RestController("/runner")
class RunnerController(
    val runnerService: RunnerService,
) {
    @PostMapping("/format")
    fun formatSnippet(
        @AuthenticationPrincipal jwt: Jwt,
        @RequestBody snippetContentDTO: SnippetContentDTO,
    ): ResponseEntity<String> = ResponseEntity.ok(runnerService.formatSnippet(snippetContentDTO, jwt.subject, jwt.tokenValue))
}
