package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.service.SnippetService
import org.springframework.stereotype.Service

@Service
class SnippetApiService(private val snippetService: SnippetService) {
    fun createSnippet(
        id: Long,
        name: String,
        content: String,
        language: String,
        extension: String,
    ): SnippetBO {
        try {
            val snippetBOToSave = SnippetBO(id, name, content, language, extension)
            return snippetService.saveSnippet(snippetBOToSave)
        } catch (e: Exception) {
            throw e
        }
    }

    fun updateSnippet(
        id: Long,
        content: String,
    ): SnippetBO? {
        try {
            val existingSnippetBO = snippetService.getSnippetById(id)
            val snippetBOToUpdate =
                SnippetBO(existingSnippetBO.getId(), existingSnippetBO.getName(), content, existingSnippetBO.getLanguage(), existingSnippetBO.getExtension())
            return snippetService.updateSnippet(snippetBOToUpdate, id)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getAllSnippets(): List<SnippetBO>? {
        try {
            return snippetService.getAllSnippets()
        } catch (e: Exception) {
            throw e
        }
    }
}
