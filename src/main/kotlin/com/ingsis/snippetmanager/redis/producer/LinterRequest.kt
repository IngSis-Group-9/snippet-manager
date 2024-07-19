package com.ingsis.snippetmanager.redis.producer

import com.ingsis.snippetmanager.model.dto.LinterRulesDTO

data class LinterRequest(
    val userId: String,
    val snippet: String,
    val linterRules: LinterRulesDTO,
)
