package com.parque.models

import kotlinx.serialization.Serializable

@Serializable
data class UtilizadorDTO(
    val idUtilizador: Int? = null,
    val nome: String,
    val tipo: String,
    val email: String,
    val password: String
)
