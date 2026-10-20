package it.unicam.cs.mpgc.rpg130278.model;

public record RisultatoEvento(boolean successo, String messaggio) {
    public RisultatoEvento {
        if (messaggio == null || messaggio.isBlank()) {
            throw new IllegalArgumentException("Il messaggio del risultato non può essere vuoto");
        }
    }
}