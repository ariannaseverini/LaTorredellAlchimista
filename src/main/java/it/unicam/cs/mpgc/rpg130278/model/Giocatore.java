package it.unicam.cs.mpgc.rpg130278.model;

public class Giocatore {
    private static final int MAX_PUNTI_VITA = 100;
    private int puntiVita;
    private Spada spada;

    public Giocatore(Spada spada){
        if(spada == null) throw new IllegalArgumentException("La spada è obbligatoria");
        this.spada = spada;
        this.puntiVita = MAX_PUNTI_VITA;
    }

    public int getPuntiVita() {
        return puntiVita;
    }

    public Spada getSpada() {
        return spada;
    }
}
