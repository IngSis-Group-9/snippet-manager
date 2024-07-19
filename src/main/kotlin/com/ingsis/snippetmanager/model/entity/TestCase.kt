package com.ingsis.snippetmanager.model.entity

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "test_cases")
data class TestCase(
    var name: String,
    @ElementCollection
    var input: List<String> = ArrayList(),
    @ElementCollection
    var output: List<String> = emptyList(),
    @ElementCollection
    var envVars: Map<String, String> = emptyMap(),
    @ManyToOne
    @JoinColumn(name = "snippet_id", nullable = false)
    val snippet: Snippet,
) : BaseEntity()
