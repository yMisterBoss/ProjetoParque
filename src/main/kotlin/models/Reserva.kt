package com.parque.models

import org.jetbrains.exposed.sql.Table
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.javatime.datetime

object Reserva : Table("Reserva") {
    val idReserva = integer("idReserva").autoIncrement()
    val utilizadorId = integer("UtilizadorID")
    val estacionamentoId = integer("EstacionamentoID")
    val dataInicio = datetime("DataInicio")
    val dataSaida = datetime("DataSaida")
    val statusReserva = varchar("StatusReserva", 45)

    override val primaryKey = PrimaryKey(idReserva)
}

@Serializable
data class ReservaDTO(
    val idReserva: Int? = null,
    val utilizadorId: Int,
    val estacionamentoId: Int,
    val dataInicio: String,
    val dataSaida: String,
    val statusReserva: String
)
