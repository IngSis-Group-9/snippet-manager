package com.ingsis.snippetmanager.model.entity

import com.ingsis.snippetmanager.model.enums.ComplianceEnum
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne

@Entity
data class Snippet(
    val name: String,
    val language: String,
    val extension: String,
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: User,
    val compliance: ComplianceEnum,
    @ManyToMany
    @JoinTable(
        name = "snippet_shared_with",
        joinColumns = [JoinColumn(name = "snippet_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")],
    )
    val sharedWith: MutableSet<User> = HashSet(),
) : BaseEntity()
