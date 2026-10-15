package it.unicam.cs.mpgc.rpg130278.model;

public class Spada {
    private final int danno;

    public Spada(int danno){
        if(danno <= 0) throw new IllegalArgumentException("Il valore del danno non puo essere inferiore o uguale a 0");
        this.danno = danno;
    }
    public int getDanno(){
        return this.danno;
    }

}
