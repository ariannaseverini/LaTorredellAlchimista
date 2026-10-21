package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;

// Interfaccia che rappresenta un generico evento attivabile dietro una porta.
public interface Evento {
    RisultatoEvento esegui(Giocatore giocatore);
}