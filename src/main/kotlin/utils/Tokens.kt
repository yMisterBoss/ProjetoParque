package com.parque.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.Date

fun generateToken(email: String): String {
    val algorithm = Algorithm.HMAC256("secret") // Use uma key segura
    return JWT.create()
        .withIssuer("http://localhost:8080/")
        .withAudience("http://localhost:8080/")
        .withClaim("email", email)
        .withExpiresAt(Date(System.currentTimeMillis() + 600000)) // 10min
        .sign(algorithm)
}