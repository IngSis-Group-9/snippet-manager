package com.ingsis.snippetmanager.model.entity

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "test_cases")
class TestCase(
    val name: String,
    @ElementCollection
    val input: List<String> = ArrayList(),
    @ElementCollection
    val output: List<String> = emptyList(),
    @ElementCollection
    val envVars: Map<String, String> = emptyMap(),
    @ManyToOne
    @JoinColumn(name = "snippet_id", nullable = false)
    val snippet: Snippet,
) : BaseEntity()
