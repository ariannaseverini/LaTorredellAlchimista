package it.unicam.cs.mpgc.rpg130278.model;

import it.unicam.cs.mpgc.rpg130278.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;

public class Porta {
    private final Evento evento;

    public Porta(Evento evento){
        if (evento == null) throw new IllegalArgumentException();
        this.evento = evento;
    }

    public Evento getEvento() {
        return evento;
    }

    public RisultatoEvento apri(Giocatore giocatore){
        if(giocatore == null) throw new IllegalArgumentException();
        return evento.esegui(giocatore);
    }
}
