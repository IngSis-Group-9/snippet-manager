package com.ingsis.snippetmanager.model.entity

import com.ingsis.snippetmanager.model.enums.RuleType
import com.ingsis.snippetmanager.model.enums.RuleValueType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "rules")
data class Rule(
    val name: String,
    @Enumerated(EnumType.STRING)
    val ruleType: RuleType,
    @Enumerated(EnumType.STRING)
    val valueType: RuleValueType,
    val defaultValue: String,
) : BaseEntity()
