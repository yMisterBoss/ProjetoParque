package com.parque

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import com.parque.models.*

fun Route.utilizadorRoutes() {
    route("/utilizadores") {
        get {
            val list = transaction {
                Utilizadores.selectAll().map {
                    UtilizadorDTO(
                        idUtilizador = it[Utilizadores.idUtilizador],
                        nome = it[Utilizadores.nome],
                        tipo = it[Utilizadores.tipo],
                        dataCriacao = it[Utilizadores.dataCriacao],
                        email = it[Utilizadores.email],
                        password = it[Utilizadores.password]
                    )
                }
            }
            call.respond(list)
        }

        post {
            val dto = call.receive<UtilizadorDTO>()
            transaction {
                Utilizadores.insert {
                    it[nome] = dto.nome
                    it[tipo] = dto.tipo
                    it[dataCriacao] = org.jetbrains.exposed.sql.javatime.CurrentDateTime
                    it[email] = dto.email
                    it[password] = dto.password
                }
            }
            call.respondText("Criado com sucesso")
        }
    }

    post("/auth/login") {
        data class LoginRequest(val email: String, val password: String)

        val req = call.receive<LoginRequest>()
        val user = transaction {
            Utilizadores.select { Utilizadores.email eq req.email }.firstOrNull()
        }
        if (user != null && user[Utilizadores.password] == req.password) {
            call.respondText("Login OK")
        } else {
            call.respondText("Email ou senha inválidos")
        }
    }
}
