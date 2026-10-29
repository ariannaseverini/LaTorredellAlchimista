package it.unicam.cs.mpgc.rpg130278.model;

public class Giocatore {
    private static final int MAX_PUNTI_VITA = 100;
    private int puntiVita;
    private Spada spada;

    public Giocatore(Spada spada) {
        this(spada, MAX_PUNTI_VITA);
    }

    public Giocatore(Spada spada, int puntiVita) {
        if (spada == null) throw new IllegalArgumentException("La spada è obbligatoria");
        if (puntiVita <= 0 || puntiVita > MAX_PUNTI_VITA) {
            throw new IllegalArgumentException("I punti vita devono essere tra 1 e " + MAX_PUNTI_VITA);
        }
        this.spada = spada;
        this.puntiVita = puntiVita;
    }

    public int getPuntiVita() {
        return puntiVita;
    }

    public Spada getSpada() {
        return spada;
    }

    public void equipaggiaSpada(Spada nuovaSpada) {
        if (nuovaSpada == null) {
            throw new IllegalArgumentException("La nuova spada non può essere null");
        }
        this.spada = nuovaSpada;
    }

    public void subisciDanno(int danno) {
        if (danno <= 0) throw new IllegalArgumentException("Il valore del danno non puo essere inferiore o uguale a 0");
        puntiVita = Math.max(0, this.puntiVita - danno);

    }

    public boolean isMorto() {
        return (getPuntiVita() == 0);
    }

}
