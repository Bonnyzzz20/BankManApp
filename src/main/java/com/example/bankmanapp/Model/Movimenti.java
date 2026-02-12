package com.example.bankmanapp.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimenti")
public class Movimenti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    // FK verso il Conto (Obbligatoria)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conto", nullable = false)
    private Conto conto;

    // FK verso la Carta (Opzionale: es. nulla per un bonifico)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_carta", nullable = true)
    private Carta carta;

    @Column(name = "importo", nullable = false, precision = 19, scale = 2)
    private BigDecimal importo;

    @Column(name = "tipo", nullable = false)
    private String tipo; // "DEPOSITO" o "PRELIEVO"

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    public Movimenti() {}

    // Costruttore Robusto
    public Movimenti(Conto conto, Carta carta, BigDecimal importo, String tipo) {
        setConto(conto);
        this.carta = carta; // Può essere null
        setImporto(importo);
        setTipo(tipo);
        this.data = LocalDateTime.now();
    }

    // Getter e Setter con Validazione
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Conto getConto() { return conto; }
    public void setConto(Conto conto) {
        if (conto == null) throw new IllegalArgumentException("Il conto è obbligatorio");
        this.conto = conto;
    }

    public Carta getCarta() { return carta; }
    public void setCarta(Carta carta) { this.carta = carta; }

    public BigDecimal getImporto() { return importo; }
    public void setImporto(BigDecimal importo) {
        if (importo == null || importo.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Importo non valido");
        }
        this.importo = importo;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) {
        if (!"DEPOSITO".equals(tipo) && !"PRELIEVO".equals(tipo)) {
            throw new IllegalArgumentException("Tipo movimento errato");
        }
        this.tipo = tipo;
    }

    public LocalDateTime getData() { return data; }
}
