package com.parque.models

import kotlinx.serialization.Serializable

@Serializable
data class ReservaDTO(
    val idReserva: Int? = null,
    val utilizadorId: Int,
    val estacionamentoId: Int,
    val dataInicio: String,
    val dataSaida: String,
    val statusReserva: String
)