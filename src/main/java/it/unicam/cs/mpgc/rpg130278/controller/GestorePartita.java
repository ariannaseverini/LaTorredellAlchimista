package it.unicam.cs.mpgc.rpg130278.controller;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.PianoDellaTorre;
import it.unicam.cs.mpgc.rpg130278.model.Porta;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;

public class GestorePartita {

    public static final int NUMERO_PIANI = 10;

    private final Giocatore giocatore;
    private PianoDellaTorre pianoAttuale;
    private StatoPartita stato;

    public GestorePartita(Giocatore giocatore) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        this.giocatore = giocatore;
        this.pianoAttuale = new PianoDellaTorre(1);
        this.stato = StatoPartita.IN_CORSO;
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

    public StatoPartita getStato() {
        return stato;
    }

    public boolean isGameOver() {
        return stato != StatoPartita.IN_CORSO;
    }

    public RisultatoEvento scegliPorta(int indicePorta) {
        if (isGameOver()) {
            throw new IllegalStateException("Impossibile scegliere una porta quando la partita è terminata.");
        }

        Porta portaScelta = pianoAttuale.getPorta(indicePorta);
        RisultatoEvento risultato = portaScelta.apri(giocatore);

        if (giocatore.isMorto()) {
            this.stato = StatoPartita.SCONFITTA;
        } else if (risultato.pianiIndietro() > 0) {
            int nuovoPiano = Math.max(1, pianoAttuale.getNumeroPiano() - risultato.pianiIndietro());
            this.pianoAttuale = new PianoDellaTorre(nuovoPiano);
        } else if (risultato.successo()) {
            if (pianoAttuale.getNumeroPiano() >= NUMERO_PIANI) {
                this.stato = StatoPartita.VITTORIA;
            } else {
                this.pianoAttuale = new PianoDellaTorre(pianoAttuale.getNumeroPiano() + 1);
            }
        }

        return risultato;
    }
}