package com.ingsis.snippetmanager.controller.snippet

import com.ingsis.snippetmanager.controller.user.UserMapperController
import com.ingsis.snippetmanager.model.bo.SnippetBO

class SnippetMapperController {
    fun convertSnippetTOToBO(snippetTO: SnippetTO): SnippetBO {
        val ownerBO = UserMapperController().convertUserTOToBO(snippetTO.getOwner())
        return SnippetBO(snippetTO.getName(), snippetTO.getType(), snippetTO.getContent(), ownerBO)
    }

    fun convertSnippetBOToTO(snippetBO: SnippetBO): SnippetTO {
        val ownerTO = UserMapperController().convertUserBOToTO(snippetBO.getOwner())
        return SnippetTO(snippetBO.getName(), snippetBO.getType(), snippetBO.getContent(), ownerTO)
    }
}
