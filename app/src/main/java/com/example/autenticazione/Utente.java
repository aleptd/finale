package com.example.autenticazione;

public class Utente {
    public String getEmailUtente() {
        return emailUtente;
    }

    public String getNome() {
        return nome;
    }

    public Utente(String emailUtente) {
        this.emailUtente = emailUtente;
    }

    String emailUtente;
    String nome;


    public Utente() {
    }


}

