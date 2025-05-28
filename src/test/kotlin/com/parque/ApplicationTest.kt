package com.parque

import kotlin.test.*
import io.ktor.server.testing.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        val response = client.get("/")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}