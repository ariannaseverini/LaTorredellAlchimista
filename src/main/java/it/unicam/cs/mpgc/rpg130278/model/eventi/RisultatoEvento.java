package it.unicam.cs.mpgc.rpg130278.model.eventi;

public record RisultatoEvento(boolean successo, String messaggio, int pianiIndietro) {
    public RisultatoEvento {
        if (messaggio == null || messaggio.isBlank()) {
            throw new IllegalArgumentException("Il messaggio del risultato non può essere vuoto");
        }
        if (pianiIndietro < 0) {
            throw new IllegalArgumentException("I piani indietro non possono essere negativi");
        }
    }

    public RisultatoEvento(boolean successo, String messaggio) {
        this(successo, messaggio, 0);
    }
}