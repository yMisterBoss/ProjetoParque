package com.parque.routes

import com.parque.models.Reserva
import com.parque.models.ReservaDTO
import com.parque.utils.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.reservaRoutes() {
    route("/api/reservas") {
        get {
            val reservas = transaction {
                Reserva.selectAll().map {
                    ReservaDTO(
                        it[Reserva.idReserva],
                        it[Reserva.utilizadorId],
                        it[Reserva.estacionamentoId],
                        it[Reserva.dataInicio].toString(),
                        it[Reserva.dataSaida].toString(),
                        it[Reserva.statusReserva])
                }
            }
            call.respond(reservas)
        }

        post {
            val novaReserva = call.receive<ReservaDTO>()
            transaction {
                inserirReserva(novaReserva.utilizadorId, novaReserva.estacionamentoId, novaReserva.statusReserva)
            }
            call.respond(HttpStatusCode.Created)
        }
    }
}