package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.model.bo.SnippetBO
import com.ingsis.snippetmanager.model.de.User
import com.ingsis.snippetmanager.model.mapper.SnippetMapperModel
import com.ingsis.snippetmanager.repository.SnippetRepository
import org.springframework.stereotype.Service

@Service
class SnippetService(private val snippetRepository: SnippetRepository) {
    fun saveSnippet(snippetBO: SnippetBO): SnippetBO {
        val snippetDE = SnippetMapperModel().convertSnippetBOToDE(snippetBO)
        return SnippetMapperModel().convertSnippetDEToBO(snippetRepository.save(snippetDE))
    }

    fun getSnippetById(id: Long): SnippetBO {
        val snippetDE = snippetRepository.findById(id).orElseThrow { Exception("Snippet not found") }
        return SnippetMapperModel().convertSnippetDEToBO(snippetDE)
    }

    fun getAllSnippets(): List<SnippetBO> {
        return snippetRepository.findAll().map { SnippetMapperModel().convertSnippetDEToBO(it) }
    }

    fun getAllSnippetsByUser(user: User): List<SnippetBO> {
        return snippetRepository.findAllByOwnerOrSharedWith(user).map { SnippetMapperModel().convertSnippetDEToBO(it) }
    }

    fun deleteSnippetById(id: Long) {
        snippetRepository.deleteById(id)
    }

    fun updateSnippet(
        snippetBO: SnippetBO,
        id: Long,
    ): SnippetBO {
        val existingSnippetDE = snippetRepository.findById(id).orElseThrow { Exception("Snippet not found") }

        existingSnippetDE.setContent(snippetBO.getContent())

        return SnippetMapperModel().convertSnippetDEToBO(snippetRepository.save(existingSnippetDE))
    }

    fun shareSnippet(
        snippetId: Long,
        friend: User,
    ): SnippetBO? {
        val snippetDE = snippetRepository.findById(snippetId).orElseThrow { Exception("Snippet not found") }
        snippetDE.addSharedWith(friend)
        val updatedSnippetDE = snippetRepository.save(snippetDE)
        return SnippetMapperModel().convertSnippetDEToBO(updatedSnippetDE)
    }
}
