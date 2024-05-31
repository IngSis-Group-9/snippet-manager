package com.ingsis.snippetmanager.controller.snippet

import com.ingsis.snippetmanager.controller.user.UserMapperController
import com.ingsis.snippetmanager.controller.user.UserTO
import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.service.SnippetService
import org.springframework.stereotype.Service

@Service
class SnippetApiService(private val snippetService: SnippetService) {
    fun createSnippet(
        name: String,
        type: String,
        content: String,
        owner: UserTO,
    ): SnippetBO {
        try {
            val ownerBO = UserMapperController().convertUserTOToBO(owner)
            val snippetBOToSave = SnippetBO(name, type, content, ownerBO)
            return snippetService.saveSnippet(snippetBOToSave)
        } catch (e: Exception) {
            throw e
        }
    }

    fun updateSnippet(
        id: Long,
        content: String,
        owner: UserTO,
    ): SnippetBO? {
        try {
            val ownerBO = UserMapperController().convertUserTOToBO(owner)
            val existingSnippetBO = snippetService.getSnippetById(id)
            val snippetBOToUpdate = SnippetBO(existingSnippetBO.getName(), existingSnippetBO.getType(), content, ownerBO)
            return snippetService.updateSnippet(snippetBOToUpdate, id)
        } catch (e: Exception) {
            throw e
        }
    }
}
