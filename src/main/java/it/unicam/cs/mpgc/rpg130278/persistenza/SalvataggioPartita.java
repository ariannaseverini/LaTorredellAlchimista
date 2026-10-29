package it.unicam.cs.mpgc.rpg130278.persistenza;

public record SalvataggioPartita(int piano, int puntiVita, int dannoSpada) {

    public SalvataggioPartita {
        if (piano < 1) {
            throw new IllegalArgumentException("Il piano deve essere almeno 1");
        }
        if (puntiVita <= 0) {
            throw new IllegalArgumentException("Un giocatore morto non si può salvare");
        }
        if (dannoSpada <= 0) {
            throw new IllegalArgumentException("Il danno della spada deve essere maggiore di 0");
        }
    }
}