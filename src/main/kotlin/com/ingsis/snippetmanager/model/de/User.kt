package com.ingsis.snippetmanager.model.de

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id", nullable = false)
    private val id: String,
    @OneToMany(mappedBy = "owner")
    private val ownedSnippets: Set<SnippetDE> = HashSet(),
    @ManyToMany(mappedBy = "sharedWith")
    private val sharedSnippets: Set<SnippetDE> = HashSet()
) {
    fun getId(): String {
        return id
    }

    fun getOwnedSnippets(): Set<SnippetDE> {
        return ownedSnippets
    }

    fun getSharedSnippets(): Set<SnippetDE> {
        return sharedSnippets
    }
}