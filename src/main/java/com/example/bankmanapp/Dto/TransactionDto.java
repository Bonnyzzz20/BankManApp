package com.example.bankmanapp.Dto;

import com.example.bankmanapp.Model.Movimenti;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record TransactionDto(

        Long idConto,
        BigDecimal saldoAlMomento,
        List<Movimenti> movimenti,
        LocalDateTime dataReport



) {
}
