package it.unicam.cs.mpgc.rpg130278.model;

public class Mostro {
    private final String nome;
    private final int danno;
    private int puntiVita;

    public Mostro(String nome, int puntiVita, int danno) {
        if(nome == null || nome.isBlank()) throw new IllegalArgumentException();
        if(puntiVita <= 0) throw new IllegalArgumentException();
        if (danno <= 0) throw new IllegalArgumentException();
        this.nome = nome;
        this.puntiVita = puntiVita;
        this.danno = danno;
    }

    public String getNome() {
        return this.nome;
    }

    public int getPuntiVita() {
        return this.puntiVita;
    }

    public int getDanno() {
        return this.danno;
    }

    public void subisciDanno(int danno) {
        if (danno <= 0) throw new IllegalArgumentException("Il valore del danno non puo essere inferiore o uguale a 0");
        puntiVita = Math.max(0, this.puntiVita - danno);
    }

    public boolean isMorto() {
        return (getPuntiVita() == 0);
    }
}