package com.parque

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    connectToDatabase()
    configureSerialization()
    configureAuthentication()
    configureRouting()

    install(CORS) {
        anyHost() // Em produção, use host específico: host("localhost:3000")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
    }
    println("Servidor iniciado com sucesso.")
}

fun connectToDatabase() {
    val url = "jdbc:mysql://localhost:3306/esan-dsg11"
    val user = "root"
    val password = ""

    try {
        Database.connect(
            url = url,
            driver = "com.mysql.cj.jdbc.Driver",
            user = user,
            password = password
        )
        println("Base de dados conectado.")

    }catch(e: Exception){

        println("Erro ao conectar a Base de dados: ${e.message}")
        return
    }
}



fun Application.configureAuthentication() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = "ktor-sample"
            verifier(
                JWT.require(Algorithm.HMAC256("secret"))
                    .withAudience("http://localhost:8080/")
                    .withIssuer("http://localhost:8080/")
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("username").asString() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token inválido ou ausente")
            }
        }
    }
}
