package com.parque.models

import org.jetbrains.exposed.sql.Table
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.javatime.datetime

object HistoricoReserva : Table("HistoricoReserva") {
    val idHistoricoReserva = integer("IDHistoricoReserva").autoIncrement()
    val estacionamentoId = integer("EstacionamentoID")
    val reservaId = integer("ReservaID")
    val utilizadorId = integer("UtilizadorID")
    val dataEntrada = datetime("DataEntrada")
    val dataSaida = datetime("DataSaida")
    val statusReserva = varchar("StatusReserva", 45)

    override val primaryKey = PrimaryKey(idHistoricoReserva)
}


