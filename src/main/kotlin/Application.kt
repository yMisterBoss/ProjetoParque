package com.parque

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureTemplating()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
