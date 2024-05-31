package com.ingsis.snippetmanager.model.mapper

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.de.SnippetDE

class SnippetMapperModel {
    fun convertSnippetBOToDE(snippetBO: SnippetBO): SnippetDE {
        val ownerDE = UserMapperModel().convertUserBOToDE(snippetBO.getOwner())
        return SnippetDE(snippetBO.getName(), snippetBO.getType(), snippetBO.getContent(), ownerDE)
    }

    fun convertSnippetDEToBO(snippetDE: SnippetDE): SnippetBO {
        val ownerBO = UserMapperModel().convertUserDEToBo(snippetDE.getOwner())
        return SnippetBO(snippetDE.getName(), snippetDE.getType(), snippetDE.getContent(), ownerBO)
    }
}
