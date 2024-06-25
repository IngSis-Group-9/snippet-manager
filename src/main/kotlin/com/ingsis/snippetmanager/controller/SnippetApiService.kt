package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.de.User
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
        owner: User,
    ): SnippetBO {
        try {
            val snippetBOToSave = SnippetBO(id, name, content, language, extension, owner)
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
                SnippetBO(
                    existingSnippetBO.getId(),
                    existingSnippetBO.getName(),
                    content,
                    existingSnippetBO.getLanguage(),
                    existingSnippetBO.getExtension(),
                    existingSnippetBO.getOwner(),
                )
            return snippetService.updateSnippet(snippetBOToUpdate, id)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getAllSnippetsByUser(userId: String): List<SnippetBO>? {
        try {
            // los id empiezan con google-oauth2| y luego el id en si, quiero sacarle la primer parte

            return snippetService.getAllSnippetsByUser(userId)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getSnippetById(id: Long): SnippetBO? {
        try {
            return snippetService.getSnippetById(id)
        } catch (e: Exception) {
            throw e
        }
    }
}
