package it.unicam.cs.mpgc.rpg130278.controller;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import it.unicam.cs.mpgc.rpg130278.persistenza.ArchivioPartite;
import it.unicam.cs.mpgc.rpg130278.persistenza.SalvataggioPartita;

import java.util.Optional;

public class ServizioSalvataggio {

    private final ArchivioPartite archivio;

    public ServizioSalvataggio(ArchivioPartite archivio) {
        if (archivio == null) {
            throw new IllegalArgumentException("L'archivio non può essere null.");
        }
        this.archivio = archivio;
    }

    public void salva(GestorePartita partita) {
        if (partita == null) {
            throw new IllegalArgumentException("La partita non può essere null.");
        }
        if (partita.getStato() != StatoPartita.IN_CORSO) {
            throw new IllegalStateException("Si può salvare solo tra un piano e l'altro.");
        }
        Giocatore giocatore = partita.getGiocatore();
        archivio.salva(new SalvataggioPartita(
                partita.getPianoAttuale().getNumeroPiano(),
                giocatore.getPuntiVita(),
                giocatore.getSpada().getDanno()));
    }

    public Optional<GestorePartita> riprendi() {
        Optional<SalvataggioPartita> trovato = archivio.carica();
        if (trovato.isEmpty()) {
            return Optional.empty();
        }
        SalvataggioPartita salvataggio = trovato.get();
        try {
            Giocatore giocatore = new Giocatore(new Spada(salvataggio.dannoSpada()), salvataggio.puntiVita());
            return Optional.of(new GestorePartita(giocatore, salvataggio.piano()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public void elimina() {
        archivio.elimina();
    }
}