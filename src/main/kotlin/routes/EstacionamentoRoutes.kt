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
import org.jetbrains.exposed.sql.update

data class EstacionamentoDTO(val localizacao: String, val status: String)

fun Route.estacionamentoRoutes() {

    route("/api/estacionamentos") {

        get {
            val lista = transaction {
                Estacionamento.selectAll().map {
                    EstacionamentoDTO(
                        id = it[Estacionamento.idEstacionamento],
                        localizacao = it[Estacionamento.localizacao],
                        status = it[Estacionamento.status]
                    )
                }
            }
            call.respond(lista)
        }

        post("/update") {
            val estados = call.receive<List<EstacionamentoDTO>>()

            transaction {
                estados.forEach { lugar ->
                    Estacionamento.update({ Estacionamento.localizacao eq lugar.localizacao }) {
                        it[status] = lugar.status
                    }
                }
            }

            call.respondText("Estado dos lugares atualizado com sucesso.")
        }
    }
}