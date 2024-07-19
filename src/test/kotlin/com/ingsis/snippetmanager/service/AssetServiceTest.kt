package com.ingsis.snippetmanager.service

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate

@ExtendWith(MockitoExtension::class)
class AssetServiceTest {
    @Mock
    private lateinit var restTemplate: RestTemplate

    private lateinit var assetService: AssetService

    private val bucketUrl = "http://mockbucket.com"

    @BeforeEach
    fun setup() {
        assetService = AssetService(restTemplate, bucketUrl)
    }

    @Test
    fun `test 001 - should succeed when getting a snippet`() {
        val id = "123"
        val response = ResponseEntity("snippet content", HttpStatus.OK)

        `when`(restTemplate.getForEntity("$bucketUrl/$id", String::class.java)).thenReturn(response)

        val result = assetService.getSnippet(id)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals("snippet content", result.body)
    }

    @Test
    fun `test 002 - should return bad request when getting a snippet`() {
        val id = "123"

        `when`(restTemplate.getForEntity("$bucketUrl/$id", String::class.java)).thenThrow(RuntimeException::class.java)

        val result = assetService.getSnippet(id)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }

    @Test
    fun `test 003 - should succeed when saving a snippet`() {
        val id = "1"
        val content = "new snippet content"
        val response = ResponseEntity("snippet saved", HttpStatus.OK)
        val request = HttpEntity(content, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON })

        `when`(restTemplate.postForEntity("$bucketUrl/$id", request, String::class.java)).thenReturn(response)

        val result = assetService.saveSnippet(id, content)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals("snippet saved", result.body)
    }

    @Test
    fun `test 004 - should return bad request when saving a snippet`() {
        val id = "1"
        val content = "new snippet content"
        val request = HttpEntity(content, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON })

        `when`(restTemplate.postForEntity("$bucketUrl/$id", request, String::class.java)).thenThrow(RuntimeException::class.java)

        val result = assetService.saveSnippet(id, content)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }

    @Test
    fun `test 005 - should succeed when updating a snippet`() {
        val id = "1"
        val content = "updated snippet content"
        val response = ResponseEntity("snippet updated", HttpStatus.OK)
        val request = HttpEntity(content, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON })

        `when`(restTemplate.postForEntity("$bucketUrl/$id", request, String::class.java)).thenReturn(response)

        val result = assetService.updateSnippet(id, content)

        assertEquals(HttpStatus.OK, result.statusCode)
        assertEquals("snippet updated", result.body)
    }

    @Test
    fun `test 006 - should return bad request when updating a snippet`() {
        val id = "1"
        val content = "updated snippet content"
        val request = HttpEntity(content, HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON })

        `when`(restTemplate.postForEntity("$bucketUrl/$id", request, String::class.java)).thenThrow(RuntimeException::class.java)

        val result = assetService.updateSnippet(id, content)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }

    @Test
    fun `test 007 - should succeed when deleting a snippet by id`() {
        val id = "1"

        doNothing().`when`(restTemplate).delete("$bucketUrl/$id")

        val result = assetService.deleteSnippet(id)

        assertEquals(HttpStatus.OK, result.statusCode)
    }

    @Test
    fun `test 008 - should return bad request when deleting a snippet`() {
        val id = "1"

        doThrow(RuntimeException::class.java).`when`(restTemplate).delete("$bucketUrl/$id")

        val result = assetService.deleteSnippet(id)

        assertEquals(HttpStatus.BAD_REQUEST, result.statusCode)
    }
}
