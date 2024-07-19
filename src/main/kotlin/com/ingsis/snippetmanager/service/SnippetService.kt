package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.exception.NotFoundException
import com.ingsis.snippetmanager.exception.ServiceUnavailableException
import com.ingsis.snippetmanager.model.dto.CreateSnippetDTO
import com.ingsis.snippetmanager.model.dto.SnippetDTO
import com.ingsis.snippetmanager.model.dto.UpdateSnippetDTO
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
        createSnippetDTO: CreateSnippetDTO,
        userId: String,
    ): SnippetDTO {
        log.info("Creating snippet for user: $userId (name: ${createSnippetDTO.name}, content: ${createSnippetDTO.content})")
        val user = userService.findUserById(userId)
        val newSnippet =
            snippetRepository.save(
                Snippet(
                    name = createSnippetDTO.name,
                    language = createSnippetDTO.language,
                    extension = createSnippetDTO.extension,
                    owner = user,
                    compliance = ComplianceEnum.PENDING,
                ),
            )

        val response = assetService.saveSnippet(newSnippet.id!!, createSnippetDTO.content)
        if (response.statusCode.is2xxSuccessful) {
            return SnippetDTO(
                newSnippet.id,
                newSnippet.name,
                createSnippetDTO.content,
                newSnippet.language,
                newSnippet.extension,
                newSnippet.owner.name,
                newSnippet.compliance,
            )
        } else {
            log.error("Error saving snippet content (id: ${newSnippet.id}, content: ${createSnippetDTO.content})")
            throw ServiceUnavailableException("Error saving snippet content (id: ${newSnippet.id}, content: ${createSnippetDTO.content})")
        }
    }

    fun getSnippet(id: String): SnippetDTO {
        log.info("Getting snippet with id: $id")
        val snippet =
            snippetRepository.findById(id).orElseThrow {
                log.error("Snippet with id: $id not found")
                NotFoundException("Snippet with id: $id not found")
            }

        val content = assetService.getSnippet(id)
        if (content.statusCode.is2xxSuccessful) {
            return SnippetDTO(
                snippet.id!!,
                snippet.name,
                content.body!!,
                snippet.language,
                snippet.extension,
                snippet.owner.name,
                snippet.compliance,
            )
        } else {
            log.error("Error getting snippet content id: $id")
            throw ServiceUnavailableException("Error getting snippet content id: $id")
        }
    }

    fun getAllSnippets(
        userId: String,
        snippetName: String,
    ): List<SnippetDTO> {
        log.info("Getting all snippets for user: $userId (name: $snippetName)")
        val user = userService.findUserById(userId)
        val snippets = snippetRepository.findAllByOwnerOrSharedWith(user, snippetName)

        return snippets.map {
            val content = assetService.getSnippet(it.id!!)
            if (content.statusCode.is2xxSuccessful) {
                SnippetDTO(it.id, it.name, content.body!!, it.language, it.extension, it.owner.name, it.compliance)
            } else {
                log.error("Error getting snippet content id: ${it.id}")
                throw ServiceUnavailableException("Error getting snippet content id: ${it.id}")
            }
        }
    }

    fun deleteSnippet(id: String) {
        snippetRepository.deleteById(id)
    }

    @Transactional
    fun updateSnippet(updateSnippetDTO: UpdateSnippetDTO): SnippetDTO {
        val snippet =
            snippetRepository.findById(updateSnippetDTO.id).orElseThrow {
                log.error("Snippet with id: ${updateSnippetDTO.id} not found")
                NotFoundException("Snippet with id: ${updateSnippetDTO.id} not found")
            }
        val deleteResponse = assetService.deleteSnippet(snippet.id!!)
        if (!deleteResponse.statusCode.is2xxSuccessful) {
            throw ServiceUnavailableException("Error deleting snippet content id: ${updateSnippetDTO.id}")
        }

        val updateResponse = assetService.saveSnippet(snippet.id, updateSnippetDTO.content)
        if (updateResponse.statusCode.is2xxSuccessful) {
            return SnippetDTO(
                snippet.id,
                snippet.name,
                updateSnippetDTO.content,
                snippet.language,
                snippet.extension,
                snippet.owner.name,
                snippet.compliance,
            )
        } else {
            log.error("Error saving snippet content (id: ${snippet.id}, content: ${updateSnippetDTO.content})")
            throw ServiceUnavailableException("Error saving snippet content (id: ${snippet.id}, content: ${updateSnippetDTO.content})")
        }
    }

    @Transactional
    fun shareSnippet(
        shareId: String,
        id: String,
    ): SnippetDTO {
        val snippet =
            snippetRepository.findById(id).orElseThrow {
                log.error("Snippet with id: $id not found")
                NotFoundException("Snippet with id: $id not found")
            }
        val user = userService.findUserById(shareId)
        snippet.sharedWith.add(user)
        val updatedSnippet = snippetRepository.save(snippet)

        val content = assetService.getSnippet(updatedSnippet.id!!)
        if (content.statusCode.is2xxSuccessful) {
            return SnippetDTO(
                updatedSnippet.id,
                updatedSnippet.name,
                content.body!!,
                updatedSnippet.language,
                updatedSnippet.extension,
                updatedSnippet.owner.name,
                updatedSnippet.compliance,
            )
        } else {
            log.error("Error getting snippet content id: $id")
            throw ServiceUnavailableException("Error getting snippet content id: $id")
        }
    }

    @Transactional
    fun setSnippetsComplianceToPending(userId: String): List<Snippet> {
        log.info("Updating all snippet compliance for user: $userId")
        val snippets = snippetRepository.findAllByOwnerId(userId)
        snippets.forEach {
            it.compliance = ComplianceEnum.PENDING
        }
        snippetRepository.saveAll(snippets)
        return snippets
    }

    @Transactional
    fun updateSnippetCompliance(
        snippetId: String,
        userId: String,
        complianceEnum: ComplianceEnum,
    ) {
        log.info("Updating snippet compliance for snippet: $snippetId for user: $userId")
        val snippet =
            snippetRepository.findByIdAndOwnerId(snippetId, userId).orElseThrow {
                log.error("Snippet with id: $snippetId and owner: $userId not found")
                NotFoundException("Snippet with id: $snippetId and owner: $userId not found")
            }
        snippet.compliance = complianceEnum
        snippetRepository.save(snippet)
    }
}
