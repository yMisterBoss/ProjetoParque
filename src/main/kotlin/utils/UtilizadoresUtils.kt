package com.parque.utils

import com.parque.models.Utilizadores
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update


fun listarUtilizadores() {
    transaction {
        Utilizadores.selectAll().forEach(){
            println("ID: ${it[Utilizadores.idUtilizador]}, Nome: ${it[Utilizadores.nome]}, Email: ${it[Utilizadores.email]}")
        }
    }
}

fun inserirUtilizador(nome: String, email: String, password: String, tipo: String) {
    transaction {
        Utilizadores.insert {
            it[Utilizadores.nome] = nome
            it[Utilizadores.email] = email
            it[Utilizadores.password] = password
            it[Utilizadores.tipo] = tipo
        }
        println("Utilizador inserido com sucesso!")
    }
}

fun atualizarUtilizador(id: Int, nome: String, email: String, password: String, tipo: String) {
    transaction {
        Utilizadores.update({ Utilizadores.idUtilizador eq id }) {
            it[Utilizadores.nome] = nome
            it[Utilizadores.email] = email
            it[Utilizadores.password] = password
            it[Utilizadores.tipo] = tipo
        }
        println("Utilizador com ID $id atualizado com sucesso!")
    }
}

fun eliminarUtilizador(id: Int) {
    transaction {
        Utilizadores.deleteWhere{ Utilizadores.idUtilizador eq id }
        println("Utilizador com ID $id eliminado com sucesso!")
    }
}