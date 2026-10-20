package it.unicam.cs.mpgc.rpg130278.model;

// Interfaccia che rappresenta un generico evento attivabile dietro una porta.
public interface Evento {
    RisultatoEvento esegui(Giocatore giocatore);
}