package com.ingsis.snippetmanager.redis.consumer

import com.ingsis.snippetmanager.model.enums.ComplianceEnum

data class LinterResponse(
    val userId: String,
    val snippetId: String,
    val linterResult: ComplianceEnum,
)
