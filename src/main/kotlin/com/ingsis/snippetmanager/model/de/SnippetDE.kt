package com.ingsis.snippetmanager.model.de

import com.ingsis.snippetmanager.model.ComplianceEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
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
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private val owner: User,
    @Column(name = "compliance", nullable = false)
    private val compliance: ComplianceEnum,
    @ManyToMany
    @JoinTable(
        name = "snippet_shared_with",
        joinColumns = [JoinColumn(name = "snippet_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
    )
    private val sharedWith: MutableSet<User> = HashSet(),
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

    fun getOwner(): User {
        return owner
    }

    fun getCompliance(): ComplianceEnum {
        return compliance
    }

    fun getSharedWith(): Set<User> {
        return sharedWith
    }

    fun addSharedWith(user: User) {
        sharedWith.add(user)
    }
}
