package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;

public class EventoTrappola implements Evento {
    private final int pianiDaScendere;

    public EventoTrappola(int pianiDaScendere) {
        if (pianiDaScendere <= 0) {
            throw new IllegalArgumentException("I piani da scendere devono essere almeno 1");
        }
        this.pianiDaScendere = pianiDaScendere;
    }

    public int getPianiDaScendere() {
        return pianiDaScendere;
    }

    @Override
    public RisultatoEvento esegui(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        return new RisultatoEvento(true,
                "Sei caduto in una trappola e vieni spinto verso il basso di " + pianiDaScendere + " piani!",
                pianiDaScendere);
    }
}