package com.parque.utils

import com.parque.models.Estacionamento
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class EstacionamentosUtils  : Table("Estacionamento") {
    val idEstacionamento = integer("idEstacionamento").autoIncrement()
    val localizacao = varchar("localizacao", 200)
    val status = varchar("status", 200)

    override val primaryKey = PrimaryKey(idEstacionamento)
}

fun inserirEstacionamento( localizacao: String) {
    Estacionamento.insert {
        it[Estacionamento.localizacao] = localizacao
    }
}

fun atualizarEstacionamento(status: String, localizacao: String) {
    transaction {
        val estacionamento = Estacionamento
            .select { Estacionamento.localizacao eq localizacao }
            .firstOrNull()

        if (estacionamento != null) {
            val id = estacionamento[Estacionamento.idEstacionamento]

            Estacionamento.update({ Estacionamento.idEstacionamento eq id }) {
                it[Estacionamento.status] = status
                // it[Estacionamento.localizacao] = localizacao // opcional: geralmente não precisa atualizar
            }
        } else {
            println("⚠️ Estacionamento com localizacao '$localizacao' não encontrado.")
        }
    }
}


fun eliminarEstacionamento(id: Int) {
    Estacionamento.deleteWhere { Estacionamento.idEstacionamento eq id }
}
