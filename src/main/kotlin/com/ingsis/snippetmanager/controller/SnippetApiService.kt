package com.ingsis.snippetmanager.controller

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.service.SnippetService
import org.springframework.stereotype.Service
import java.io.File

@Service
class SnippetApiService(private val snippetService: SnippetService){

    fun createSnippet(name: String, type: String, file: File): SnippetBO{
        try{
            val content = file.readText()
            val snippetBOToSave = SnippetBO(name, type, content)
            return snippetService.saveSnippet(snippetBOToSave)
        }catch(e : Exception){
            throw e
        }
    }
}