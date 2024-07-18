package com.ingsis.snippetmanager.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AssetService(
    val rest: RestTemplate,
    @Value("\${azureBucket.url}") val bucketUrl: String,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(AssetService::class.java)

    fun getSnippet(id: String): ResponseEntity<String> {
        try {
            log.info("Getting snippet: { id: $id })")
            return rest.getForEntity("$bucketUrl/$id", String::class.java)
        } catch (e: Exception) {
            log.error("Error getting snippet with id: $id", e)
            return ResponseEntity.badRequest().build()
        }
    }

    fun saveSnippet(
        id: String,
        content: String,
    ): ResponseEntity<String> {
        try {
            log.info("Saving snippet: { id: $id, content: $content })")
            val request = HttpEntity(content, getHeaders())
            return rest.postForEntity("$bucketUrl/$id", request, String::class.java)
        } catch (e: Exception) {
            log.error("Error saving snippet with id: $id", e)
            return ResponseEntity.badRequest().build()
        }
    }

    fun updateSnippet(
        id: String,
        content: String,
    ): ResponseEntity<String> {
        try {
            log.info("Updating snippet: { id: $id, content: $content })")
            val request = HttpEntity(content, getHeaders())
            return rest.postForEntity("$bucketUrl/$id", request, String::class.java)
        } catch (e: Exception) {
            log.error("Error updating snippet with id: $id", e)
            return ResponseEntity.badRequest().build()
        }
    }

    fun deleteSnippet(id: String): ResponseEntity<String> {
        try {
            log.info("Deleting snippet: { id: $id })")
            rest.delete("$bucketUrl/$id")
            return ResponseEntity.ok().build()
        } catch (e: Exception) {
            log.error("Error deleting snippet with id: $id", e)
            return ResponseEntity.badRequest().build()
        }
    }

    private fun getHeaders(): HttpHeaders =
        HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
        }
}
