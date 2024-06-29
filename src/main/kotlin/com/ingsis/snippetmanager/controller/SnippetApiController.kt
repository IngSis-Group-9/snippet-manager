package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.ShareSnippetRequest
import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.bo.UpdateSnippetRequest
import com.ingsis.snippetmanager.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
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
class SnippetApiController(private val snippetApiService: SnippetApiService, private val userService: UserService) {
    @PostMapping("/create")
    fun createSnippet(
        @RequestBody request: CreateSnippetRequest,
    ): ResponseEntity<SnippetBO> {
        val snippetBO = SnippetMapperController(userService).convertSnippetTOToBO(request)
        snippetApiService.createSnippet(
            snippetBO.getId(),
            snippetBO.getName(),
            snippetBO.getContent(),
            snippetBO.getLanguage(),
            snippetBO.getExtension(),
            snippetBO.getOwner(),
            snippetBO.getCompliance(),
        )
        return ResponseEntity.ok(snippetBO)
    }

    @GetMapping("/getAll")
    fun getAllSnippets(
        @RequestParam userId: String,
    ): ResponseEntity<List<SnippetBO>> {
        return ResponseEntity.ok(snippetApiService.getAllSnippetsByUser(userId))
    }

    @GetMapping("/{id}")
    fun getSnippetById(
        @PathVariable id: Long,
    ): ResponseEntity<SnippetBO> {
        val snippetBO = snippetApiService.getSnippetById(id)
        return if (snippetBO != null) {
            ResponseEntity.ok(snippetBO)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun updateSnippetById(
        @PathVariable id: Long,
        @RequestBody request: UpdateSnippetRequest,
    ): ResponseEntity<SnippetBO> {
        val updatedSnippetBO = snippetApiService.updateSnippet(id, request.content)
        return if (updatedSnippetBO != null) {
            ResponseEntity.ok(updatedSnippetBO)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping("/{snippetId}/share")
    fun shareSnippet(
        @PathVariable snippetId: Long,
        @RequestBody request: ShareSnippetRequest,
    ): ResponseEntity<SnippetBO> {
        val friend = userService.findUserById(request.userId)
        if (friend.isEmpty) {
            return ResponseEntity.notFound().build()
        }
        val sharedSnippet = snippetApiService.shareSnippet(snippetId, friend.get())
        return ResponseEntity.ok(sharedSnippet)
    }
}
