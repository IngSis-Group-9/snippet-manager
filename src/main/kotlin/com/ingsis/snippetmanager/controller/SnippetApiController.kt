package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.bo.UpdateSnippet
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@CrossOrigin("*")
@RestController
@RequestMapping("/snippets")
class SnippetApiController(private val snippetApiService: SnippetApiService) {


    @PostMapping("/create")
    fun createSnippet(
        @RequestBody snippetTO: SnippetTO,
    ): ResponseEntity<SnippetBO> {
        val snippetBO = SnippetMapperController().convertSnippetTOToBO(snippetTO)
        snippetApiService.createSnippet(snippetBO.getId(), snippetBO.getName(), snippetBO.getContent(), snippetBO.getLanguage(), snippetBO.getExtension())
        return ResponseEntity.ok(snippetBO)
    }

    @GetMapping("/getAll")
    fun getAllSnippets(): ResponseEntity<List<SnippetBO>> {
        return ResponseEntity.ok(snippetApiService.getAllSnippets())
    }

    @GetMapping("/{id}")
    fun getSnippetById(@PathVariable id: Long): ResponseEntity<SnippetBO> {
        val snippetBO = snippetApiService.getSnippetById(id)
        return if (snippetBO != null) {
            ResponseEntity.ok(snippetBO)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{id}")
    fun updateSnippetById(@PathVariable id: Long, @RequestBody updateSnippet: UpdateSnippet): ResponseEntity<SnippetBO> {
        val updatedSnippetBO = snippetApiService.updateSnippet(id, updateSnippet.content)
        return if (updatedSnippetBO != null) {
            ResponseEntity.ok(updatedSnippetBO)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
