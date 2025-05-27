package com.parque.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

fun gerarToken(username: String, role: String): String {
    return JWT.create()
        .withAudience("http://localhost:8080/")
        .withIssuer("http://localhost:8080/")
        .withClaim("username", username)
        .withClaim("role", role) // Adiciona o claim de papel
        .sign(Algorithm.HMAC256("secret")) // Substitua "secret" por uma chave segura
}