package com.parque.utils

import jdk.jfr.internal.event.EventConfiguration.timestamp
import org.jetbrains.exposed.sql.Table

object Utilizadores: Table("Utilizadores") {
    val idUtilizador = integer("id").autoIncrement()
    val nome = varchar("nome", 100)
    val email = varchar("email", 100)
    val password = varchar("password", 50)
    val tipo = varchar("nome", 100)
    
    override val primaryKey = PrimaryKey(idUtilizador, name = "PK_Utilizadores_id")
}