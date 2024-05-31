package com.ingsis.snippetmanager.model.de

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "snippets")
data class SnippetDE(
    @Column(name = "name", nullable = false)
    private val name: String,
    @Column(name = "type", nullable = false)
    private val type: String,
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private var content: String,
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private val owner: UserDE,
) {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val snippetId: Long = 0

    fun getId(): Long {
        return snippetId
    }

    fun getName(): String {
        return name
    }

    fun getType(): String {
        return type
    }

    fun getContent(): String {
        return content
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun getOwner(): UserDE {
        return owner
    }
}
