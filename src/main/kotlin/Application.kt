package com.parque

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import org.jetbrains.exposed.sql.Database
import java.sql.DriverManager

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)

}

fun Application.module() {
    connectToDatabase()
    configureTemplating()
    configureSerialization()
    configureHTTP()
    configureRouting()
}

fun connectToDatabase() {
    val url = "jdbc:mysql://localhost:3306/db_Parque"
    val user = "root"
    val password = "nUNO@2004"

    val connection = DriverManager.getConnection(url, user, password)
    println("Conectado com sucesso: $connection")
}
