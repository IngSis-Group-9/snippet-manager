package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.NotFoundException
import com.ingsis.snippetmanager.exception.ServiceUnavailableException
import com.ingsis.snippetmanager.model.dto.CreateSnippetRequest
import com.ingsis.snippetmanager.model.dto.ShareSnippetRequest
import com.ingsis.snippetmanager.model.dto.SnippetDto
import com.ingsis.snippetmanager.model.dto.UpdateSnippetRequest
import com.ingsis.snippetmanager.model.entity.Snippet
import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import com.ingsis.snippetmanager.repository.SnippetRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SnippetService(
    private val assetService: AssetService,
    private val userService: UserService,
    private val snippetRepository: SnippetRepository,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(SnippetService::class.java)

    @Transactional
    fun saveSnippet(
        request: CreateSnippetRequest,
        userId: String,
    ): SnippetDto {
        log.info("Creating snippet for user: $userId (name: ${request.name}, content: ${request.content})")
        val user =
            userService.findUserById(userId).orElseThrow {
                log.error("User with id: $userId not found")
                NotFoundException("User with id: $userId not found")
            }

        val newSnippet =
            snippetRepository.save(
                Snippet(
                    name = request.name,
                    language = request.language,
                    extension = request.extension,
                    owner = user,
                    compliance = ComplianceEnum.PENDING,
                ),
            )

        val response = assetService.saveSnippet(newSnippet.id!!, request.content)
        if (response.statusCode.is2xxSuccessful) {
            return SnippetDto(
                newSnippet.id,
                newSnippet.name,
                request.content,
                newSnippet.language,
                newSnippet.extension,
                newSnippet.owner.name,
                newSnippet.compliance,
            )
        } else {
            throw ServiceUnavailableException("Error saving snippet content (id: ${newSnippet.id}, content: ${request.content})")
        }
    }

    fun getSnippet(id: String): SnippetDto {
        log.info("Getting snippet with id: $id")
        val snippet =
            snippetRepository.findById(id).orElseThrow {
                log.error("Snippet with id: $id not found")
                NotFoundException("Snippet with id: $id not found")
            }

        val content = assetService.getSnippet(id)
        if (content.statusCode.is2xxSuccessful) {
            return SnippetDto(
                snippet.id!!,
                snippet.name,
                content.body!!,
                snippet.language,
                snippet.extension,
                snippet.owner.name,
                snippet.compliance,
            )
        } else {
            throw ServiceUnavailableException("Error getting snippet content id: $id")
        }
    }

    fun getAllSnippets(
        userId: String,
        snippetName: String,
    ): List<SnippetDto> {
        log.info("Getting all snippets for user: $userId (name: $snippetName)")
        val user =
            userService.findUserById(userId).orElseThrow {
                log.error("User with id: $userId not found")
                NotFoundException("User with id: $userId not found")
            }
        val snippets = snippetRepository.findAllByOwnerOrSharedWith(user, snippetName)

        return snippets.map {
            val content = assetService.getSnippet(it.id!!)
            if (content.statusCode.is2xxSuccessful) {
                SnippetDto(it.id, it.name, content.body!!, it.language, it.extension, it.owner.name, it.compliance)
            } else {
                throw ServiceUnavailableException("Error getting snippet content id: ${it.id}")
            }
        }
    }

    fun deleteSnippet(id: String) {
        snippetRepository.deleteById(id)
    }

    fun updateSnippet(
        request: UpdateSnippetRequest,
        id: String,
    ): SnippetDto {
        val snippet =
            snippetRepository.findById(id).orElseThrow {
                log.error("Snippet with id: $id not found")
                NotFoundException("Snippet with id: $id not found")
            }
        val deleteResponse = assetService.deleteSnippet(snippet.id!!)
        if (!deleteResponse.statusCode.is2xxSuccessful) {
            throw ServiceUnavailableException("Error deleting snippet content id: $id")
        }

        val updateResponse = assetService.saveSnippet(snippet.id!!, request.content)
        if (updateResponse.statusCode.is2xxSuccessful) {
            return SnippetDto(
                snippet.id,
                snippet.name,
                request.content,
                snippet.language,
                snippet.extension,
                snippet.owner.name,
                snippet.compliance,
            )
        } else {
            throw ServiceUnavailableException("Error saving snippet content (id: ${snippet.id}, content: ${request.content})")
        }
    }

    fun shareSnippet(
        request: ShareSnippetRequest,
        id: String,
    ): SnippetDto {
        val snippet =
            snippetRepository.findById(id).orElseThrow {
                log.error("Snippet with id: $id not found")
                NotFoundException("Snippet with id: $id not found")
            }
        val user =
            userService.findUserById(request.userId).orElseThrow {
                log.error("User with id: ${request.userId} not found")
                NotFoundException("User with id: ${request.userId} not found")
            }
        snippet.sharedWith.add(user)
        val updatedSnippet = snippetRepository.save(snippet)

        val content = assetService.getSnippet(updatedSnippet.id!!)
        if (content.statusCode.is2xxSuccessful) {
            return SnippetDto(
                updatedSnippet.id,
                updatedSnippet.name,
                content.body!!,
                updatedSnippet.language,
                updatedSnippet.extension,
                updatedSnippet.owner.name,
                updatedSnippet.compliance,
            )
        } else {
            throw ServiceUnavailableException("Error getting snippet content id: $id")
        }
    }
}
