package com.ingsis.snippetmanager.model.de

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class UserDE(
    @Column(name = "username", nullable = false, unique = true)
    private val username: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private val userId: Long = 0

    fun getId(): Long {
        return userId
    }

    fun getUsername(): String {
        return username
    }
}
