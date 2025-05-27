package com.parque.routes

import com.parque.HistoricoDTO
import com.parque.models.HistoricoReserva
import com.parque.utils.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.historicoRoutes() {
    route("/api/historico") {
        get {
            val list = transaction {
                HistoricoReserva.selectAll().map {
                    HistoricoDTO(
                        it[HistoricoReserva.idHistoricoReserva],
                        it[HistoricoReserva.reservaId],
                        it[HistoricoReserva.statusReserva],
                        it[HistoricoReserva.dataEntrada])
                }
            }
            call.respond(list)
        }

        post {
            val novo = call.receive<HistoricoDTO>()
            transaction { inserirHistorico(novo.idReserva, novo.status, novo.dataHora) }
            call.respond(HttpStatusCode.Created)
        }
    }
}