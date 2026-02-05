package com.example.bankmanapp.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentiDto(

        int id,
        int idConto, // FK verso Conto
        BigDecimal importo,
        String tipo, // "DEPOSITO" o "PRELIEVO"
        LocalDateTime data


) {
}
