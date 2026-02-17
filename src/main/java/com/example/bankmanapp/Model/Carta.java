package com.example.bankmanapp.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carte")
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; // PK

    @Column(nullable = false, unique = true, length = 16)
    private String numeroCarta;

    @Column(nullable = false)
    private String titolare;

    @Column(nullable = false)
    private LocalDate dataScadenza;

    @Column(nullable = false, length = 3)
    private String cvv;

    @Column(nullable = false)
    private String pin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCarta tipo; // DEBITO o CREDITO

    private Double fido; // Solo per carte di credito

    @Column(nullable = false)
    private Double massimaleMensile;

    @Column(nullable = false)
    private boolean attiva = true;

    // FK verso il Conto
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conto", nullable = false)
    private Conto conto;

    // Relazione verso i movimenti
    @OneToMany(mappedBy = "carta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Movimenti> listaMovimenti = new ArrayList<>();


    public Carta() {}


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }


    public String getNumeroCarta() { return numeroCarta; }
    public void setNumeroCarta(String numeroCarta) { this.numeroCarta = numeroCarta; }


    public TipoCarta getTipo() { return tipo; }
    public void setTipo(TipoCarta tipo) { this.tipo = tipo; }


    public Conto getConto() { return conto; }
    public void setConto(Conto conto) { this.conto = conto; }


    public boolean isAttiva() { return attiva; }
    public void setAttiva(boolean attiva) { this.attiva = attiva; }


    public String getTitolare() { return titolare; }
    public void setTitolare(String titolare) { this.titolare = titolare; }


    public LocalDate getDataScadenza() { return dataScadenza; }
    public void setDataScadenza(LocalDate dataScadenza) { this.dataScadenza = dataScadenza; }


    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }


    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }


    public Double getFido() { return fido; }
    public void setFido(Double fido) { this.fido = fido; }


    public Double getMassimaleMensile() { return massimaleMensile; }
    public void setMassimaleMensile(Double massimaleMensile) { this.massimaleMensile = massimaleMensile; }
}