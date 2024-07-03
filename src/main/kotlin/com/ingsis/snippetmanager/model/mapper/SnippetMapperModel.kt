package com.ingsis.snippetmanager.model.mapper

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.de.Snippet

class SnippetMapperModel {
    fun convertSnippetDEToBO(snippet: Snippet): SnippetBO {
        return SnippetBO(
            snippet.getId(),
            snippet.getName(),
            snippet.getContent(),
            snippet.getLanguage(),
            snippet.getExtension(),
            snippet.getOwner().getName(),
            snippet.getCompliance(),
        )
    }
}
