package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.ComplianceEnum
import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.service.UserService

class SnippetMapperController(private val userService: UserService) {
    fun convertSnippetTOToBO(createSnippetRequest: CreateSnippetRequest): SnippetBO {
        val user = userService.findUserById(createSnippetRequest.getOwnerId()).orElse(null)
        return SnippetBO(
            createSnippetRequest.getId(),
            createSnippetRequest.getName(),
            createSnippetRequest.getContent(),
            createSnippetRequest.getLanguage(),
            createSnippetRequest.getExtension(),
            user,
            ComplianceEnum.PENDING,
        )
    }

    fun convertSnippetBOToTO(snippetBO: SnippetBO): CreateSnippetRequest {
        return CreateSnippetRequest(
            snippetBO.getId(),
            snippetBO.getName(),
            snippetBO.getContent(),
            snippetBO.getLanguage(),
            snippetBO.getExtension(),
            snippetBO.getOwner().getId(),
        )
    }
}
