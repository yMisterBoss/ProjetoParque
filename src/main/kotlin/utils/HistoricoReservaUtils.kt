package com.parque.utils

import com.parque.models.HistoricoReserva
import com.parque.models.Reserva
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.time.LocalDateTime

object HistoricoReservaUtils  : Table("HistoricoReserva") {
    val idHistorico = integer("idHistorico").autoIncrement()
    val idReserva = integer("idReserva") references Reserva.idReserva
    val status = varchar("status", 50)
    val dataHora = varchar("dataHora", 100)

    override val primaryKey = PrimaryKey(idHistorico)
}

fun inserirHistorico(idReserva: Int, status: String, dataHora: LocalDateTime) {
    HistoricoReserva.insert {
        it[HistoricoReserva.idHistoricoReserva] = idReserva
        it[HistoricoReserva.statusReserva] = status
        it[HistoricoReserva.dataEntrada] = dataHora
    }
}

fun atualizarHistorico(id: Int, status: String, dataHora: LocalDateTime) {
    HistoricoReserva.update({ HistoricoReserva.idHistoricoReserva eq id }) {
        it[HistoricoReserva.statusReserva] = status
        it[HistoricoReserva.dataEntrada] = dataHora
    }
}

fun eliminarHistorico(id: Int) {
    HistoricoReserva.deleteWhere { HistoricoReserva.idHistoricoReserva eq id }
}
