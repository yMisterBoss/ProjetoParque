package com.parque

import com.parque.utils.LdrData
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.p
import kotlinx.html.title

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respondText("Hello World!")

        }
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        get("/form"){

        }
        get("/sensor") {
            call.respondHtml {
                head {

                }
                body {
                    h1 { +"Leitura do Sensor LDR" }
                    p { +"Página funcionando corretamente com HTML DSL." }
                }
            }
        }
        post("/sensor") {
            val dados = call.receive<LdrData>()
            println("Valor recebido do LDR: ${dados.valor}")
            call.respondHtml {

                body {
                    h1 { +"Leitura do Sensor LDR" }
                    p { +"Valor recebido: ${dados.valor}" }
                }
            }
            // Aqui você pode salvar no banco com Exposed se quiser
            call.respondText("OK")
        }


    }

}
