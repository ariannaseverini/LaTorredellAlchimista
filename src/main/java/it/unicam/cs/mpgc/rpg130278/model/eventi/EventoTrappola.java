package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;

public class EventoTrappola implements Evento {
    private final int danno;

    public EventoTrappola(int danno){
        if (danno <= 0) throw new IllegalArgumentException();
        this.danno = danno;
    }

    public int getDanno() {
        return danno;
    }

    @Override
    public RisultatoEvento esegui(Giocatore giocatore){
        if(giocatore == null) throw new IllegalArgumentException();
        giocatore.subisciDanno(this.danno);
        if(giocatore.isMorto()) return new RisultatoEvento(false, "Sei caduto in una trappola mortale e sei stato sconfitto!");
        else return new RisultatoEvento(true, "Sei caduto in una trappola e hai subito " + this.danno + " danni!");
    }
}
