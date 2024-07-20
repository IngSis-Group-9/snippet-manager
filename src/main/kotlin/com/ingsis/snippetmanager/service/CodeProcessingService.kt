package com.ingsis.snippetmanager.service

import com.ingsis.snippetmanager.logs.CorrelationIdFilter.Companion.CORRELATION_ID_KEY
import com.ingsis.snippetmanager.model.dto.FormatterRequest
import com.ingsis.snippetmanager.model.dto.FormatterRulesDTO
import com.ingsis.snippetmanager.model.dto.InterpretRequest
import com.ingsis.snippetmanager.model.dto.InterpretResponse
import com.newrelic.agent.deps.org.slf4j.MDC
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class CodeProcessingService(
    private val rest: RestTemplate,
    @Value("\${codeProcessing.url}") val runnerUrl: String,
) {
    private val log = org.slf4j.LoggerFactory.getLogger(CodeProcessingService::class.java)

    fun interpretSnippet(
        snippet: String,
        inputs: List<String>,
        envs: Map<String, String>,
        token: String,
    ): ResponseEntity<InterpretResponse> {
        log.info("Sending interpret request with content: $snippet, inputs: $inputs, envs: $envs")
        try {
            val request = HttpEntity(InterpretRequest(snippet, inputs, envs), getHeaders(token))
            return rest.postForEntity("$runnerUrl/interpret", request, InterpretResponse::class.java)
        } catch (e: Exception) {
            log.error("Error while interpreting snippet", e)
            return ResponseEntity.badRequest().build()
        }
    }

    fun formatSnippet(
        snippet: String,
        formatterRules: FormatterRulesDTO,
        token: String,
    ): ResponseEntity<String> {
        try {
            log.info("Sending format request with content: $snippet, formatterRules: $formatterRules")
            val request = HttpEntity(FormatterRequest(snippet, formatterRules), getHeaders(token))
            val response = rest.postForEntity("$runnerUrl/format", request, String::class.java)
            return response
        } catch (e: Exception) {
            log.error("Error while formatting snippet", e)
            return ResponseEntity.badRequest().build()
        }
    }

    private fun getHeaders(token: String): HttpHeaders =
        HttpHeaders().apply {
            contentType = MediaType.APPLICATION_JSON
            set("Authorization", "Bearer $token")
            set("X-Correlation-Id", MDC.get(CORRELATION_ID_KEY))
        }
}
