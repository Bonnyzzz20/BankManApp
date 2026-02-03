package com.example.bankmanapp.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name="movimenti")

public class Movimenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_conto", nullable = false)
    private Long idConto;// FK verso Conto

    @Column(name = "importo", nullable = false, precision = 19, scale = 2)
    private BigDecimal importo;

    @Column(name = "tipo", nullable = false)
    private String tipo; // "DEPOSITO" o "PRELIEVO"

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    public Movimenti() {}

    public Movimenti(Long idConto, BigDecimal importo, String tipo) {
        setIdConto(idConto);
        setImporto(importo);
        setTipo(tipo);
        this.data = LocalDateTime.now();
    }

    // Getter
    public Long getIdConto() { return idConto; }
    public BigDecimal getImporto() { return importo; }
    public String getTipo() { return tipo; }
    public LocalDateTime getData() { return data; }


    public void setIdConto(Long idConto) {
        if (idConto == null || idConto <= 0) throw new IllegalArgumentException("ID conto invalido");
        this.idConto = idConto;
    }

    public void setImporto(BigDecimal importo) {
        if (importo == null || importo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("L'importo deve essere positivo");
        }
        this.importo = importo;
    }

    public void setTipo(String tipo) {
        if (!"DEPOSITO".equals(tipo) && !"PRELIEVO".equals(tipo)) {
            throw new IllegalArgumentException("Tipo non ammesso");
        }
        this.tipo = tipo;
    }
}
