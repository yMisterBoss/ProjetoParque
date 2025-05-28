package com.parque.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp

object Utilizadores : Table("Utilizadores") {
    val idUtilizador = integer("idUtilizadores").autoIncrement()
    val nome = varchar("Nome_Utilizador", 245)
    val tipo = varchar("Tipo", 45)
    val dataCriacao = timestamp("Data_Criacao")
    val email = varchar("Email", 100)
    val password = varchar("Password", 255)

    override val primaryKey = PrimaryKey(idUtilizador)
}

