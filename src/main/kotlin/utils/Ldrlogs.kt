package com.parque.utils

import jdk.jfr.internal.event.EventConfiguration.timestamp
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.lang.System.currentTimeMillis

object LdrLogs : Table() {
    val id = integer("id").autoIncrement()
    val valor = integer("valor")
    val dataHora = long("dataHora").default(currentTimeMillis())
    override val primaryKey = PrimaryKey(id)
}

fun salvarLdr(valor: Int) {
    transaction {
        LdrLogs.insert {
            it[LdrLogs.valor] = valor

        }
    }
}
