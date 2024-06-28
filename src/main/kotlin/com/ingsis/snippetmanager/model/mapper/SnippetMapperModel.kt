package com.ingsis.snippetmanager.model.mapper

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.de.SnippetDE

class SnippetMapperModel {
    fun convertSnippetBOToDE(snippetBO: SnippetBO): SnippetDE {
        return SnippetDE(
            snippetBO.getName(),
            snippetBO.getContent(),
            snippetBO.getLanguage(),
            snippetBO.getExtension(),
            snippetBO.getOwner(),
            snippetBO.getCompliance(),
        )
    }

    fun convertSnippetDEToBO(snippetDE: SnippetDE): SnippetBO {
        return SnippetBO(
            snippetDE.getId(),
            snippetDE.getName(),
            snippetDE.getContent(),
            snippetDE.getLanguage(),
            snippetDE.getExtension(),
            snippetDE.getOwner(),
            snippetDE.getCompliance(),
        )
    }
}
