package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.ComplianceEnum
import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.de.Snippet
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
        compliance: ComplianceEnum,
    ): SnippetBO {
        try {
            val snippet = Snippet(name, content, language, extension, owner, compliance)
            return snippetService.saveSnippet(snippet)
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
                    existingSnippetBO.getAuthor(),
                    existingSnippetBO.getCompliance(),
                )
            return snippetService.updateSnippet(snippetBOToUpdate, id)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getAllSnippetsByUser(
        user: User,
        snippetName: String,
    ): List<SnippetBO>? {
        try {
            return snippetService.getAllSnippetsByUser(user, snippetName)
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

    fun shareSnippet(
        snippetId: Long,
        friend: User,
    ): SnippetBO? {
        try {
            return snippetService.shareSnippet(snippetId, friend)
        } catch (e: Exception) {
            throw e
        }
    }

    fun deleteSnippetById(id: Long) {
        try {
            snippetService.deleteSnippetById(id)
        } catch (e: Exception) {
            throw e
        }
    }
}
