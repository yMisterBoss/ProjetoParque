package com.parque.routes

import com.parque.models.Estacionamento
import com.parque.models.EstacionamentoDTO
import com.parque.utils.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.estacionamentoRoutes() {
    route("/api/estacionamentos") {
        get {
            val ests = transaction {
                Estacionamento.selectAll().map {
                    EstacionamentoDTO(it[Estacionamento.idEstacionamento], it[Estacionamento.status], it[Estacionamento.localizacao])
                }
            }
            call.respond(ests)
        }

        post {
            val novo = call.receive<EstacionamentoDTO>()
            transaction { inserirEstacionamento( novo.localizacao) }
            call.respond(HttpStatusCode.Created)
        }
    }
}