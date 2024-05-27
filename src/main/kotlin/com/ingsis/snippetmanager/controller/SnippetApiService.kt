package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.service.SnippetService
import org.springframework.stereotype.Service

@Service
class SnippetApiService(private val snippetService: SnippetService) {
    fun createSnippet(
        name: String,
        type: String,
        content: String,
    ): SnippetBO {
        try {
            val snippetBOToSave = SnippetBO(name, type, content)
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
            val snippetBOToUpdate = SnippetBO(existingSnippetBO.getName(), existingSnippetBO.getType(), content)
            return snippetService.updateSnippet(snippetBOToUpdate, id)
        } catch (e: Exception) {
            throw e
        }
    }
}
