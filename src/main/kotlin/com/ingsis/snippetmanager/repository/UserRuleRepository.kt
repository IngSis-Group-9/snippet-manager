package com.ingsis.snippetmanager.repository

import com.ingsis.snippetmanager.model.entity.UserRule
import com.ingsis.snippetmanager.model.enums.RuleType
import org.springframework.data.jpa.repository.JpaRepository

interface UserRuleRepository : JpaRepository<UserRule, String> {
    fun findAllByOwnerIdAndRuleRuleType(
        ownerId: String,
        ruleType: RuleType,
    ): List<UserRule>
}
