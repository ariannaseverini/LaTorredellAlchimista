package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Spada;

public class EventoForziere implements Evento {
    private final Spada nuovaSpada;

    public EventoForziere(Spada nuovaSpada){
        if(nuovaSpada == null) throw new IllegalArgumentException();
        this.nuovaSpada = nuovaSpada;
    }

    public Spada getNuovaSpada() {
        return nuovaSpada;
    }

    @Override
    public RisultatoEvento esegui(Giocatore giocatore){
        if(giocatore == null) throw new IllegalArgumentException();

        int dannoAttuale = giocatore.getSpada().getDanno();

        if (nuovaSpada.getDanno() > dannoAttuale) {
            giocatore.equipaggiaSpada(nuovaSpada);
            return new RisultatoEvento(true, "Hai aperto un forziere e equipaggiato una spada migliore con danno " + nuovaSpada.getDanno() + "!");
        } else {
            return new RisultatoEvento(true, "Hai aperto un forziere con una spada da " + nuovaSpada.getDanno() + " danni, ma la tua attuale (" + dannoAttuale + ") è migliore. L'hai lasciata lì.");
        }
    }

}

