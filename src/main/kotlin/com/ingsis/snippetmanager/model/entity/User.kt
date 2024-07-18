package com.ingsis.snippetmanager.model.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    override val id: String,
    val name: String,
    val email: String,
    @OneToMany(mappedBy = "owner")
    val ownedSnippets: Set<Snippet> = HashSet(),
    @ManyToMany(mappedBy = "sharedWith")
    val sharedSnippets: Set<Snippet> = HashSet(),
) : BaseEntity()
