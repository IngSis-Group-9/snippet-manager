package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO

class SnippetMapperController {
    fun convertSnippetTOToBO(snippetTO: SnippetTO): SnippetBO {
        return SnippetBO(snippetTO.getId(), snippetTO.getName(), snippetTO.getContent(), snippetTO.getLanguage(), snippetTO.getExtension())
    }

    fun convertSnippetBOToTO(snippetBO: SnippetBO): SnippetTO {
        return SnippetTO(snippetBO.getId(), snippetBO.getName(), snippetBO.getContent(), snippetBO.getLanguage(), snippetBO.getExtension())
    }
}
