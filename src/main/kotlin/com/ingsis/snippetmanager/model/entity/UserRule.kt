package com.ingsis.snippetmanager.model.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
data class UserRule(
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    val owner: User,
    var value: String,
    var isActive: Boolean,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "rule_id", nullable = false)
    val rule: Rule,
) : BaseEntity()
