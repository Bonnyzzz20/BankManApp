package com.example.bankmanapp.Dto;

import java.math.BigDecimal;

public record ContoDto(

        int id,
        int idUtente,
        String iban,
        BigDecimal saldo


) {
}
