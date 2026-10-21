package it.unicam.cs.mpgc.rpg130278.controller;

import it.unicam.cs.mpgc.rpg130278.model.*;
import it.unicam.cs.mpgc.rpg130278.model.eventi.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorePartitaTest {

    private Spada spadaIniziale;
    private Giocatore giocatore;
    private GestorePartita gestore;

    @BeforeEach
    void setUp() {
        spadaIniziale = new Spada(10);
        giocatore = new Giocatore(spadaIniziale);
        gestore = new GestorePartita(giocatore);
    }

    @Test
    void inizializzazionePartitaCorretta() {
        assertEquals(giocatore, gestore.getGiocatore());
        assertNotNull(gestore.getPianoAttuale());
        assertEquals(1, gestore.getPianoAttuale().getNumeroPiano());
        assertFalse(gestore.isGameOver());
    }

    @Test
    void selezionaPortaConEventoInnocuoAvanzaDiPiano() {
        Porta p0 = new Porta(new EventoForziere(new Spada(15)));
        Porta p1 = new Porta(new EventoForziere(new Spada(15)));
        Porta p2 = new Porta(new EventoForziere(new Spada(15)));
        PianoDellaTorre pianoCustom = new PianoDellaTorre(1, new Porta[]{p0, p1, p2});

        gestore.setPianoAttuale(pianoCustom);

        RisultatoEvento risultato = gestore.scegliPorta(0);

        assertTrue(risultato.successo());
        assertEquals(2, gestore.getPianoAttuale().getNumeroPiano());
        assertFalse(gestore.isGameOver());
    }

    @Test
    void selezionaPortaConTrappolaMortaleMandaInGameOver() {
        Porta p0 = new Porta(new EventoTrappola(200));
        Porta p1 = new Porta(new EventoTrappola(200));
        Porta p2 = new Porta(new EventoTrappola(200));
        PianoDellaTorre pianoCustom = new PianoDellaTorre(1, new Porta[]{p0, p1, p2});

        gestore.setPianoAttuale(pianoCustom);

        RisultatoEvento risultato = gestore.scegliPorta(0);

        assertFalse(risultato.successo());
        assertTrue(gestore.isGameOver());
        assertTrue(gestore.getGiocatore().isMorto());
    }

    @Test
    void costruttoreConGiocatoreNullLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new GestorePartita(null));
    }

    @Test
    void scegliPortaConIndiceErratoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> gestore.scegliPorta(-1));
        assertThrows(IllegalArgumentException.class, () -> gestore.scegliPorta(3));
    }
}