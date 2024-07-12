package com.ingsis.snippetmanager.model.de

import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "test_cases")
class TestCase(
    @Column(name = "name", nullable = false)
    private val name: String,
    @ElementCollection
    @Column(name = "input", nullable = false)
    private val input: List<String> = ArrayList(),
    @ElementCollection
    @Column(name = "output", nullable = false)
    private val output: List<String> = emptyList(),
    @ElementCollection
    @Column(name = "env_vars", nullable = false)
    private val envVars: List<String> = emptyList(),
    @ManyToOne
    @JoinColumn(name = "snippet_id", nullable = false)
    private val snippet: Snippet,
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

    fun getInput(): List<String> {
        return input
    }

    fun getOutput(): List<String> {
        return output
    }

    fun getEnvVars(): List<String> {
        return envVars
    }

    fun getSnippet(): Snippet {
        return snippet
    }
}
