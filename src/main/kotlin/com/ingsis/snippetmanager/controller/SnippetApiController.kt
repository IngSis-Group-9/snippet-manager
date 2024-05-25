package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
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
        @RequestParam("file") file: MultipartFile
    ): ResponseEntity<SnippetBO>{
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(null)
        }

        // Save the file to a temporary location
        val tempFile = File.createTempFile("snippet-", ".tmp")
        file.transferTo(tempFile)

        val snippetBO = snippetApiService.createSnippet(name, type, tempFile)

        // Delete the temporary file
        Files.deleteIfExists(Paths.get(tempFile.toURI()))

        return ResponseEntity.ok(snippetBO)
    }
}