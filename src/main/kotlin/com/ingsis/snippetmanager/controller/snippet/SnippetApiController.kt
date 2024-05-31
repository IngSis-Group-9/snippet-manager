package com.ingsis.snippetmanager.controller.snippet

import com.ingsis.snippetmanager.controller.user.UserTO
import com.ingsis.snippetmanager.model.bo.SnippetBO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@RequestMapping("/snippets")
class SnippetApiController(private val snippetApiService: SnippetApiService) {
    @PostMapping("/upload")
    fun uploadSnippet(
        @RequestParam("name") name: String,
        @RequestParam("type") type: String,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("owner") owner: UserTO,
    ): ResponseEntity<SnippetBO> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(null)
        }
        val tempFile = File.createTempFile("snippet-", ".tmp")
        file.transferTo(tempFile)
        val snippetBO = snippetApiService.createSnippet(name, type, tempFile.readText(), owner)
        Files.deleteIfExists(Paths.get(tempFile.toURI()))
        return ResponseEntity.ok(snippetBO)
    }

    @PostMapping("/update")
    fun updateSnippet(
        @RequestParam("id") id: Long,
        @RequestParam("file") file: MultipartFile,
        @RequestParam("owner") owner: UserTO,
    ): ResponseEntity<SnippetBO> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(null)
        }
        val tempFile = File.createTempFile("snippet-", ".tmp")
        file.transferTo(tempFile)
        val snippetBO = snippetApiService.updateSnippet(id, tempFile.readText(), owner)
        Files.deleteIfExists(Paths.get(tempFile.toURI()))
        return ResponseEntity.ok(snippetBO)
    }

    @PostMapping("/uploadFromEditor")
    fun uploadFromEditor(
        @RequestParam("name") name: String,
        @RequestParam("type") type: String,
        @RequestBody content: String,
        @RequestParam("owner") owner: UserTO,
    ): ResponseEntity<SnippetBO> {
        val snippetBO = snippetApiService.createSnippet(name, type, content, owner)
        return ResponseEntity.ok(snippetBO)
    }

    @PostMapping("/updateFromEditor")
    fun updateFromEditor(
        @RequestParam("id") id: Long,
        @RequestBody content: String,
        @RequestParam("owner") owner: UserTO,
    ): ResponseEntity<SnippetBO> {
        val snippetBO = snippetApiService.updateSnippet(id, content, owner)
        return ResponseEntity.ok(snippetBO)
    }
}