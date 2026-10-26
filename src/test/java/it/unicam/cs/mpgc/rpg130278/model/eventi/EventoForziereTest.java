package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventoForziereTest {

    private Spada spadaIniziale;
    private Giocatore giocatore;

    @BeforeEach
    void setUp() {
        spadaIniziale = new Spada(10);
        giocatore = new Giocatore(spadaIniziale);
    }

    @Test
    void bonusValidoVieneMemorizzato() {
        assertEquals(5, new EventoForziere(5).getBonusDanno());
    }

    @Test
    void bonusZeroLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new EventoForziere(0));
    }

    @Test
    void bonusNegativoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new EventoForziere(-3));
    }

    @Test
    void eseguiAumentaIlDannoDellaSpadaDelGiocatore() {
        RisultatoEvento risultato = new EventoForziere(5).esegui(giocatore);

        assertTrue(risultato.successo());
        assertEquals(15, giocatore.getSpada().getDanno());
    }

    @Test
    void eseguiNonModificaLaVecchiaSpada() {
        new EventoForziere(5).esegui(giocatore);

        assertEquals(10, spadaIniziale.getDanno());
        assertNotSame(spadaIniziale, giocatore.getSpada());
    }

    @Test
    void eseguiConGiocatoreNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new EventoForziere(5).esegui(null));
    }
}