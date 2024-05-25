package com.ingsis.snippetmanager.model.de

import jakarta.persistence.*

@Entity
@Table(name = "snippets")
data class SnippetDE(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long = 0,

    @Column(name = "name", nullable = false)
    private val name: String,

    @Column(name = "type", nullable = false)
    private val type: String,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private val content: String,
){

    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getContent(): String {
        return content
    }
}