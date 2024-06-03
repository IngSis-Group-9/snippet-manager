package com.ingsis.snippetmanager.model.de

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "snippets")
data class SnippetDE(
    @Column(name = "name", nullable = false)
    private val name: String,
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private var content: String,
    @Column(name = "language", nullable = false)
    private val language: String,
    @Column(name = "extension", nullable = false)
    private val extension: String,
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0

    fun getId(): Long {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getContent(): String {
        return content
    }

    fun getLanguage(): String {
        return language
    }

    fun getExtension(): String {
        return extension
    }

    fun setContent(content: String) {
        this.content = content
    }
}
