package it.unicam.cs.mpgc.rpg130278.controller;

import it.unicam.cs.mpgc.rpg130278.model.Azione;
import it.unicam.cs.mpgc.rpg130278.model.Combattimento;
import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Mostro;
import it.unicam.cs.mpgc.rpg130278.model.PianoDellaTorre;
import it.unicam.cs.mpgc.rpg130278.model.Porta;
import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoCombattimento;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;

public class GestorePartita {

    public static final int NUMERO_PIANI = 10;

    private final Giocatore giocatore;
    private PianoDellaTorre pianoAttuale;
    private StatoPartita stato;
    private Combattimento combattimentoInCorso;

    public GestorePartita(Giocatore giocatore) {
        this(giocatore, 1);
    }

    public GestorePartita(Giocatore giocatore, int numeroPiano) {
        if (giocatore == null) {
            throw new IllegalArgumentException("Il giocatore non può essere null.");
        }
        if (numeroPiano < 1 || numeroPiano > NUMERO_PIANI) {
            throw new IllegalArgumentException("Il piano deve essere tra 1 e " + NUMERO_PIANI);
        }
        this.giocatore = giocatore;
        this.pianoAttuale = new PianoDellaTorre(numeroPiano);
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

    public Combattimento getCombattimentoInCorso() {
        return combattimentoInCorso;
    }

    public boolean isGameOver() {
        return stato == StatoPartita.VITTORIA || stato == StatoPartita.SCONFITTA;
    }

    public RisultatoEvento scegliPorta(int indicePorta) {
        if (isGameOver()) {
            throw new IllegalStateException("Impossibile scegliere una porta quando la partita è terminata.");
        }
        if (stato == StatoPartita.IN_COMBATTIMENTO) {
            throw new IllegalStateException("Prima devi terminare il combattimento in corso.");
        }

        Porta portaScelta = pianoAttuale.getPorta(indicePorta);

        if (portaScelta.getEvento() instanceof EventoCombattimento evento) {
            combattimentoInCorso = evento.avvia(giocatore);
            stato = StatoPartita.IN_COMBATTIMENTO;
            Mostro mostro = evento.getMostro();
            return new RisultatoEvento(true, "Un mostro ti sbarra la strada: " + mostro.getNome()
                    + " (HP " + mostro.getPuntiVita() + ", danno " + mostro.getDanno() + ").");
        }

        RisultatoEvento risultato = portaScelta.apri(giocatore);
        applicaRisultato(risultato);
        return risultato;
    }

    public String eseguiTurno(Azione azione) {
        if (stato != StatoPartita.IN_COMBATTIMENTO) {
            throw new IllegalStateException("Non c'è nessun combattimento in corso.");
        }

        String racconto = combattimentoInCorso.eseguiTurno(azione);

        if (combattimentoInCorso.isFinito()) {
            boolean vinto = combattimentoInCorso.isVinto();
            combattimentoInCorso = null;
            if (vinto) {
                stato = StatoPartita.IN_CORSO;
                avanzaDiPiano();
            } else {
                stato = StatoPartita.SCONFITTA;
            }
        }
        return racconto;
    }

    private void applicaRisultato(RisultatoEvento risultato) {
        if (giocatore.isMorto()) {
            stato = StatoPartita.SCONFITTA;
        } else if (risultato.pianiIndietro() > 0) {
            int nuovoPiano = Math.max(1, pianoAttuale.getNumeroPiano() - risultato.pianiIndietro());
            pianoAttuale = new PianoDellaTorre(nuovoPiano);
        } else if (risultato.successo()) {
            avanzaDiPiano();
        }
    }

    private void avanzaDiPiano() {
        if (pianoAttuale.getNumeroPiano() >= NUMERO_PIANI) {
            stato = StatoPartita.VITTORIA;
        } else {
            pianoAttuale = new PianoDellaTorre(pianoAttuale.getNumeroPiano() + 1);
        }
    }
}