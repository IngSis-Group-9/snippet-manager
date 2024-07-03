package com.ingsis.snippetmanager.model.de

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @Column(name = "id", nullable = false)
    private val id: String,
    @Column(name = "name", nullable = false)
    private val name: String,
    @Column(name = "email", nullable = false)
    private val email: String,
    @OneToMany(mappedBy = "owner")
    private val ownedSnippets: Set<Snippet> = HashSet(),
    @ManyToMany(mappedBy = "sharedWith")
    private val sharedSnippets: Set<Snippet> = HashSet(),
) {
    fun getId(): String {
        return id
    }

    fun getName(): String {
        return name
    }

    fun getEmail(): String {
        return email
    }

    fun getOwnedSnippets(): Set<Snippet> {
        return ownedSnippets
    }

    fun getSharedSnippets(): Set<Snippet> {
        return sharedSnippets
    }
}
