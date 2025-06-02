package com.parque
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.server.testing.*
import io.ktor.http.*
import kotlin.test.*

@Test
fun testPostUtilizador() = testApplication {
    val response = client.post("/api/utilizadores") {
        contentType(ContentType.Application.Json)
        setBody("""{"nome":"João","email":"joao@email.com","password":"1234","tipo":"user"}""")
    }
    assertEquals(HttpStatusCode.Created, response.status)
}