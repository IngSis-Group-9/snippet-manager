package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

@CrossOrigin("*")
@RestController
@RequestMapping("/snippets")
class SnippetApiController(private val snippetApiService: SnippetApiService) {

    @PostMapping("/update")
    fun updateSnippet(
        @RequestParam("id") id: Long,
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<SnippetBO> {
        if (file.isEmpty) {
            return ResponseEntity.badRequest().body(null)
        }
        val tempFile = File.createTempFile("snippet-", ".tmp")
        file.transferTo(tempFile)
        val snippetBO = snippetApiService.updateSnippet(id, tempFile.readText())
        Files.deleteIfExists(Paths.get(tempFile.toURI()))
        return ResponseEntity.ok(snippetBO)
    }

    @PostMapping("/create")
    fun createSnippet(
        @RequestBody snippetTO: SnippetTO,
    ): ResponseEntity<SnippetBO> {
        val snippetBO = SnippetMapperController().convertSnippetTOToBO(snippetTO)
        snippetApiService.createSnippet(snippetBO.getName(), snippetBO.getContent(), snippetBO.getLanguage(), snippetBO.getExtension())
        return ResponseEntity.ok(snippetBO)
    }

    @GetMapping("/getAll")
    fun getAllSnippets(): ResponseEntity<List<SnippetBO>> {
        return ResponseEntity.ok(snippetApiService.getAllSnippets())
    }
}
