package com.parque.utils

import com.parque.models.Estacionamento
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class EstacionamentosUtils  : Table("Estacionamento") {
    val idEstacionamento = integer("idEstacionamento").autoIncrement()
    val nome = varchar("nome", 100)
    val localizacao = varchar("localizacao", 200)

    override val primaryKey = PrimaryKey(idEstacionamento)
}

fun inserirEstacionamento( localizacao: String) {
    Estacionamento.insert {
        it[Estacionamento.localizacao] = localizacao
    }
}

fun atualizarEstacionamento(id: Int, status: String, localizacao: String) {
    Estacionamento.update({ Estacionamento.idEstacionamento eq id }) {
        it[Estacionamento.status] = status
        it[Estacionamento.localizacao] = localizacao

    }
}

fun eliminarEstacionamento(id: Int) {
    Estacionamento.deleteWhere { Estacionamento.idEstacionamento eq id }
}
