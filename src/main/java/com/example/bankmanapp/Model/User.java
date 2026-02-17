package com.example.bankmanapp.Model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name= "cognome", nullable = false)
    private String cognome;

    @Column (name = "codice_fiscale", nullable = false, unique = true)
    private String codiceFiscale;

    @Column(name= "email", nullable = false, unique = true) //aggiorna sulla repos
    private String email;

    @Column(name="password", nullable = false)
    private String password;


    public User() {}

    public User(int id, String nome,String cognome, String email, String password, String codiceFiscale) {
        this.id = id;
        this.nome = nome;
        this.cognome=cognome;
        this.email = email;
        this.password = password;
        this.codiceFiscale=codiceFiscale;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome non valido");
        this.nome = nome;
    }

    public String getCognome(){
        return cognome;
    }
    public void setCognome(String cognome){
        if (cognome == null || cognome.trim().isEmpty()) throw new IllegalArgumentException("Cognome non valido");
        this.cognome = cognome;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        if (!email.contains("@")) throw new IllegalArgumentException("Email non valida");
        this.email = email;
    }


    public String getCodiceFiscale() {
        return codiceFiscale;
    }
    public void setCodiceFiscale(String codiceFiscale){
        if (codiceFiscale.length() != 16 || !codiceFiscale.matches("[A-Za-z0-9]+")) {
            System.out.println("Codice fiscale non valido");
        } else {
            System.out.println("Codice fiscale valido");
            this.codiceFiscale = codiceFiscale;
        }
    }
}
