package com.parque.models

import kotlinx.serialization.Serializable

@Serializable
data class HistoricoReservaDTO(
    val idHistoricoReserva: Int? = null,
    val estacionamentoId: Int,
    val reservaId: Int,
    val utilizadorId: Int,
    val dataEntrada: String,
    val dataSaida: String,
    val statusReserva: String
)