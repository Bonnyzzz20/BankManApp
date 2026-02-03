package com.example.bankmanapp.Dto;

import java.math.BigDecimal;

public record ContoDto(

        Long id,
        Long idUtente,
        String iban,
        BigDecimal saldo


) {
}
