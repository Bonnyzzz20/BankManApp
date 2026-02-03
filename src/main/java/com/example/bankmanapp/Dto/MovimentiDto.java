package com.example.bankmanapp.Dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MovimentiDto(

        Long id,
        Long idConto, // FK verso Conto
        BigDecimal importo,
        String tipo, // "DEPOSITO" o "PRELIEVO"
        LocalDateTime data


) {
}
