package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Spada;

public class EventoForziere implements Evento {
    private final int bonusDanno;

    public EventoForziere(int bonusDanno) {
        if (bonusDanno <= 0) {
            throw new IllegalArgumentException("Il bonus al danno deve essere maggiore di 0");
        }
        this.bonusDanno = bonusDanno;
    }

    public int getBonusDanno() {
        return bonusDanno;
    }

    @Override
    public RisultatoEvento esegui(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        int nuovoDanno = giocatore.getSpada().getDanno() + bonusDanno;
        giocatore.equipaggiaSpada(new Spada(nuovoDanno));
        return new RisultatoEvento(true,
                "Hai aperto un forziere: la tua spada migliora di " + bonusDanno
                        + "! Ora infligge " + nuovoDanno + " danni.");
    }
}