package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.ComplianceEnum
import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.service.UserService

class SnippetMapperController(private val userService: UserService) {
    fun convertSnippetTOToBO(
        createSnippetRequest: CreateSnippetRequest,
        author: String,
    ): SnippetBO {
        return SnippetBO(
            createSnippetRequest.getId(),
            createSnippetRequest.getName(),
            createSnippetRequest.getContent(),
            createSnippetRequest.getLanguage(),
            createSnippetRequest.getExtension(),
            author,
            ComplianceEnum.PENDING,
        )
    }
}
