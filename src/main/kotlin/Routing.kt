package com.parque

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.parque.models.ReservaDTO
import com.parque.models.Reserva
import com.parque.models.UtilizadorDTO
import com.parque.models.Utilizadores
import com.parque.models.EstacionamentoDTO
import com.parque.models.Estacionamento
import com.parque.models.HistoricoReservaDTO
import com.parque.models.HistoricoReserva
import com.parque.models.LoginRequest
import com.parque.models.LoginResponse
import com.parque.utils.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

data class UtilizadorDTO(val idUtilizador: Int? = null, val nome: String, val email: String, val password: String, val tipo: String)
data class ReservaDTO(val idReserva: Int? = null, val idUtilizador: Int, val idEstacionamento: Int, val statusReserva: String)
data class EstacionamentoDTO(val idEstacionamento: Int? = null, val status: String, val localizacao: String)
data class HistoricoDTO(val idHistoricoReserva: Int? = null, val idReserva: Int, val status: String, val dataHora: LocalDateTime)


fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Servidor em execução!")
        }

        authenticate("auth-jwt") {
            get("/api/me") {
                val principal = call.principal<JWTPrincipal>()
                val email = principal!!.payload.getClaim("username").asString()

                // Opcional: buscar mais dados do utilizador
                val user = transaction {
                    Utilizadores.select { Utilizadores.email eq email }.firstOrNull()
                }

                if (user != null) {
                    call.respond(
                        UtilizadorDTO(
                            idUtilizador = user[Utilizadores.idUtilizador],
                            nome = user[Utilizadores.nome],
                            tipo = user[Utilizadores.tipo],
                            email = user[Utilizadores.email],
                            password = "" // nunca enviar senha!
                        )
                    )
                } else {
                    call.respond(HttpStatusCode.NotFound, "Utilizador não encontrado")
                }
            }
        }

        post("/api/login") {
            val req = call.receive<LoginRequest>()

            val user = transaction {
                Utilizadores.select { Utilizadores.email eq req.email }.firstOrNull()
            }

            if (user != null && user[Utilizadores.password] == req.password) {
                val token = JWT.create()
                    .withAudience("http://localhost:8080/")   // ⚠️ deve ser igual ao verifier
                    .withIssuer("http://localhost:8080/")     // ⚠️ igual ao verifier
                    .withClaim("username", req.email)
                    .sign(Algorithm.HMAC256("secret"))        // ⚠️ mesma secret usada no verifier

                // Aqui está a resposta com o token como texto simples
                call.respond(mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Email ou senha inválidos")
            }
        }

        route("/api/utilizadores") {
            // Listar utilizadores
            get {
                val utilizadores = transaction {
                    Utilizadores.selectAll().map {
                        UtilizadorDTO(
                            idUtilizador = it[Utilizadores.idUtilizador],
                            nome = it[Utilizadores.nome],
                            email = it[Utilizadores.email],
                            password = it[Utilizadores.password],
                            tipo = it[Utilizadores.tipo]
                        )
                    }
                }
                call.respond(utilizadores)
            }

            // Adicionar utilizador
            post {
                val novoUtilizador = call.receive<UtilizadorDTO>()
                transaction {
                    inserirUtilizador(novoUtilizador.nome, novoUtilizador.email, novoUtilizador.password, novoUtilizador.tipo)
                }
                call.respond(HttpStatusCode.Created, "Utilizador criado com sucesso!")
            }

            // Atualizar utilizador
            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@put
                }
                val utilizadorAtualizado = call.receive<UtilizadorDTO>()
                transaction {
                    atualizarUtilizador(id, utilizadorAtualizado.nome, utilizadorAtualizado.email, utilizadorAtualizado.password, utilizadorAtualizado.tipo)
                }
                call.respond(HttpStatusCode.OK, "Utilizador atualizado com sucesso!")
            }

            // Excluir utilizador
            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }
                transaction {
                    eliminarUtilizador(id)
                }
                call.respond(HttpStatusCode.OK, "Utilizador excluído com sucesso!")
            }
        }

        route("/api/reservas") {
            get {
                val reservas = transaction {
                    Reserva.selectAll().map {
                        ReservaDTO(
                            idReserva = it[Reserva.idReserva],
                            idUtilizador = it[Reserva.utilizadorId],
                            idEstacionamento = it[Reserva.estacionamentoId],
                            statusReserva = it[Reserva.statusReserva]
                        )
                    }
                }
                call.respond(reservas)
            }

            post {
                val nova = call.receive<ReservaDTO>()
                transaction {
                    inserirReserva(nova.utilizadorId, nova.estacionamentoId, nova.statusReserva)
                }
                call.respond(HttpStatusCode.Created)
            }

            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
                val atualizada = call.receive<ReservaDTO>()
                transaction {
                    atualizarReserva(id, atualizada.utilizadorId, atualizada.estacionamentoId, atualizada.statusReserva)
                }
                call.respond(HttpStatusCode.OK)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                transaction { eliminarReserva(id) }
                call.respond(HttpStatusCode.OK)
            }
        }

        route("/api/estacionamentos") {
            get {
                val ests = transaction {
                    Estacionamento.selectAll().map {
                        EstacionamentoDTO(
                            it[Estacionamento.idEstacionamento],
                            it[Estacionamento.localizacao],
                            it[Estacionamento.status]
                        )
                    }
                }
                call.respond(ests)
            }

            post {
                val novo = call.receive<EstacionamentoDTO>()
                transaction { inserirEstacionamento(novo.localizacao) }
                call.respond(HttpStatusCode.Created)
            }

            put("/{loc}") {
                val loc = call.parameters["loc"]?.toString() ?: return@put call.respond(HttpStatusCode.BadRequest)
                val e = call.receive<EstacionamentoDTO>()

                transaction {
                    atualizarEstacionamento(e.status, loc)
                }
                call.respond(HttpStatusCode.OK, "Estacionamento atualizado com sucesso")
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                transaction { eliminarEstacionamento(id) }
                call.respond(HttpStatusCode.OK)
            }
        }

        route("/api/historicos") {
            get {
                val historicos = transaction {
                    HistoricoReserva.selectAll().map {
                        HistoricoDTO(
                            it[HistoricoReserva.idHistoricoReserva],
                            it[HistoricoReserva.reservaId],
                            it[HistoricoReserva.statusReserva],
                            it[HistoricoReserva.dataEntrada])
                    }
                }
                call.respond(historicos)
            }

            post {
                val novo = call.receive<HistoricoDTO>()
                transaction { inserirHistorico(novo.idReserva, novo.status, novo.dataHora) }
                call.respond(HttpStatusCode.Created)
            }

            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
                val h = call.receive<HistoricoDTO>()
                transaction { atualizarHistorico(id, h.status, h.dataHora) }
                call.respond(HttpStatusCode.OK)
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                transaction { eliminarHistorico(id) }
                call.respond(HttpStatusCode.OK)
            }
        }
    }

}
