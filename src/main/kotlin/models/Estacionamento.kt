package com.parque.models

import org.jetbrains.exposed.sql.Table
import kotlinx.serialization.Serializable

object Estacionamento : Table("Estacionamento") {
    val idEstacionamento = integer("IDEstacionamento").autoIncrement()
    val localizacao = varchar("Localizacao", 45)
    val status = varchar("Status", 45)

    override val primaryKey = PrimaryKey(idEstacionamento)
}
