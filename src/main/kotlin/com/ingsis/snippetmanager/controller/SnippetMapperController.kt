package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.service.UserService

class SnippetMapperController(private val userService: UserService) {
    fun convertSnippetTOToBO(createSnippet: CreateSnippet): SnippetBO {
        val user = userService.findUserById(createSnippet.getOwnerId()).orElse(null)
        return SnippetBO(createSnippet.getId(), createSnippet.getName(), createSnippet.getContent(), createSnippet.getLanguage(), createSnippet.getExtension(), user)
    }

    fun convertSnippetBOToTO(snippetBO: SnippetBO): CreateSnippet {
        return CreateSnippet(snippetBO.getId(), snippetBO.getName(), snippetBO.getContent(), snippetBO.getLanguage(), snippetBO.getExtension(), snippetBO.getOwner().getId())
    }
}
