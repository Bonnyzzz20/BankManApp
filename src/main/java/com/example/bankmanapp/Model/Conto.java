package com.example.bankmanapp.Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name="conti")

public class Conto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id; //PK

    @ManyToOne(fetch = FetchType.LAZY)
    // carica i dati dell'utente solo quando  richiedi getUtente().
    @JoinColumn(name = "id_utente", nullable = false)

    //@Column(name = "id_utente", nullable = false)
    private User idUtente; // FK verso User


    @Column(name= "iban", nullable = false)
    private String iban;


    @Column(name = "saldo")
    private BigDecimal saldo;


    @Transient
    private List<Movimenti> listaMovimenti = new ArrayList<>();


    public Conto() { this.saldo = BigDecimal.ZERO; }

    public Conto(int id, User idUtente, String iban) {
        this.id = id;
        this.idUtente = idUtente;
        this.iban = iban;
        this.saldo = BigDecimal.ZERO;
    }


    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public BigDecimal getSaldo() { return saldo; }
    public void setSaldo(BigDecimal nuovoSaldo) {
        if (nuovoSaldo == null) throw new IllegalArgumentException("Il saldo non può essere null");
        this.saldo = nuovoSaldo;
    }

    public List<Movimenti> getListaMovimenti() {
        return Collections.unmodifiableList(listaMovimenti);
    }

    public String getIban() { return iban; }
    public void setIban(String iban) { this.iban = iban; }

    public User getIdUtente() { return idUtente; }
    public void setIdUtente(User idUtente) { this.idUtente = idUtente; }
}


