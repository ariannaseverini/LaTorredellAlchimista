package it.unicam.cs.mpgc.rpg130278.model.eventi;

public record RisultatoEvento(boolean successo, String messaggio, int pianiIndietro, TipoEvento tipo) {
    public RisultatoEvento {
        if (messaggio == null || messaggio.isBlank()) {
            throw new IllegalArgumentException("Il messaggio del risultato non può essere vuoto");
        }
        if (pianiIndietro < 0) {
            throw new IllegalArgumentException("I piani indietro non possono essere negativi");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Il tipo di evento non può essere nullo");
        }
    }

    public RisultatoEvento(boolean successo, String messaggio, TipoEvento tipo) {
        this(successo, messaggio, 0, tipo);
    }
}