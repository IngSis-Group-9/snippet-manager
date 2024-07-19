package com.ingsis.snippetmanager.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class RestBeanTest {
    @Test
    fun testRestBean() {
        val restBean = RestBean()
        assertNotNull(restBean.restTemplate())
    }
}
