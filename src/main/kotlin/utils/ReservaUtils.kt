package com.parque.utils

import com.parque.models.Estacionamento
import com.parque.models.Reserva
import com.parque.models.Utilizadores
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import java.sql.Time
import java.time.LocalDateTime

object ReservaUtils  : Table("Reserva") {
    val idReserva = integer("idReserva").autoIncrement()
    val idUtilizador = integer("idUtilizador") references Utilizadores.idUtilizador
    val idEstacionamento = integer("idEstacionamento") references Estacionamento.idEstacionamento
    val dataHora = varchar("dataHora", 100)

    override val primaryKey = PrimaryKey(idReserva)
}

fun inserirReserva(idUtilizador: Int, idEstacionamento: Int, Status: String) {
    Reserva.insert {
        it[Reserva.utilizadorId] = idUtilizador
        it[Reserva.estacionamentoId] = idEstacionamento
        it[Reserva.statusReserva] = Status
    }
}

fun atualizarReserva(id: Int, idUtilizador: Int, idEstacionamento: Int, statusreserva: String) {
    Reserva.update({ Reserva.idReserva eq id }) {
        it[Reserva.utilizadorId] = idUtilizador
        it[Reserva.estacionamentoId] = idEstacionamento
        it[Reserva.statusReserva] = statusReserva
    }
}

fun eliminarReserva(id: Int) {
    Reserva.deleteWhere { Reserva.idReserva eq id }
}
