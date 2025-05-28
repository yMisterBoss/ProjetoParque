package com.parque.models

import kotlinx.serialization.Serializable

@Serializable
data class EstacionamentoDTO(
    val id: Int? = null,
    val localizacao: String,
    val status: String
)
