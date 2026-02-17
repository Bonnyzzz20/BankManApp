package com.example.bankmanapp.Dto;

import com.example.bankmanapp.Model.TipoCarta;

import java.time.LocalDate;

public record CartaDto (

        int id,
        String numeroCarta,
        String titolare,
        LocalDate dataScadenza,
        String cvv,
        String pin,
        TipoCarta tipo,
        Double fido,
        Double massimaleMensile,
        boolean attiva
) {
}
