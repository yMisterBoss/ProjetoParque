package com.parque.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

object Utilizadores : Table("Utilizadores") {
    val idUtilizador = integer("idUtilizadores").autoIncrement()
    val nome = varchar("Nome_Utilizador", 245)
    val tipo = varchar("Tipo", 45)
    val dataCriacao = datetime("Data_Criacao")
    val email = varchar("Email", 100)
    val password = varchar("Password", 255)

    override val primaryKey = PrimaryKey(idUtilizador)
}

data class UtilizadorDTO(
    val idUtilizador: Int? = null,
    val nome: String,
    val tipo: String,
    val dataCriacao: LocalDateTime,
    val email: String,
    val password: String
)