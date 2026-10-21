package it.unicam.cs.mpgc.rpg130278.controller;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.PianoDellaTorre;
import it.unicam.cs.mpgc.rpg130278.model.Porta;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;

public class GestorePartita {

    private final Giocatore giocatore;
    private PianoDellaTorre pianoAttuale;
    private boolean gameOver;

    public GestorePartita(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        this.giocatore = giocatore;
        this.pianoAttuale = new PianoDellaTorre(1);
        this.gameOver = false;
    }

    public Giocatore getGiocatore() {
        return giocatore;
    }

    public PianoDellaTorre getPianoAttuale() {
        return pianoAttuale;
    }

    public void setPianoAttuale(PianoDellaTorre pianoAttuale) {
        if (pianoAttuale == null) {
            throw new IllegalArgumentException("Il piano non può essere null.");
        }
        this.pianoAttuale = pianoAttuale;
    }

    public boolean isGameOver() {
        return gameOver || giocatore.isMorto();
    }

    public RisultatoEvento scegliPorta(int indicePorta) {
        if (isGameOver()) {
            throw new IllegalStateException("Impossibile scegliere una porta quando la partita è terminata.");
        }

        Porta portaScelta = pianoAttuale.getPorta(indicePorta);
        RisultatoEvento risultato = portaScelta.apri(giocatore);

        if (giocatore.isMorto()) {
            this.gameOver = true;
        } else if (risultato.successo()) {
            // Avanza al piano successivo
            this.pianoAttuale = new PianoDellaTorre(pianoAttuale.getNumeroPiano() + 1);
        }

        return risultato;
    }
}