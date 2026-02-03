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
    private Long id; //PK

    @Column(name = "id_utente", nullable = false)
    private Long idUtente; // FK verso User

    @Column(name= "iban", nullable = false)
    private String iban;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @Transient
    private List<Movimenti> listaMovimenti = new ArrayList<>();




    public Conto() { this.saldo = BigDecimal.ZERO; }

    public Conto(Long id, Long idUtente, String iban) {
        this.id = id;
        this.idUtente = idUtente;
        this.iban = iban;
        this.saldo = BigDecimal.ZERO;
    }


    public void aggiungiMovimento(Movimenti m) {
        if (m == null) throw new IllegalArgumentException("Movimento nullo");
        if (this.id != null && !this.id.equals(m.getIdConto())) {
            throw new IllegalArgumentException("Il movimento non appartiene a questo conto");
        }
        this.listaMovimenti.add(m);
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

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

    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }
}


