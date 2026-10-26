package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventoTrappolaTest {

    private Giocatore giocatore;

    @BeforeEach
    void setUp() {
        giocatore = new Giocatore(new Spada(10));
    }

    @Test
    void pianiDaScendereValidiVengonoMemorizzati() {
        assertEquals(2, new EventoTrappola(2).getPianiDaScendere());
    }

    @Test
    void pianiDaScendereZeroLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new EventoTrappola(0));
    }

    @Test
    void pianiDaScendereNegativiLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new EventoTrappola(-1));
    }

    @Test
    void eseguiRestituisceRisultatoConIPianiDaScendere() {
        RisultatoEvento risultato = new EventoTrappola(2).esegui(giocatore);

        assertTrue(risultato.successo());
        assertEquals(2, risultato.pianiIndietro());
    }

    @Test
    void eseguiNonToglieIPuntiVitaAlGiocatore() {
        new EventoTrappola(3).esegui(giocatore);

        assertEquals(100, giocatore.getPuntiVita());
    }

    @Test
    void eseguiConGiocatoreNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new EventoTrappola(1).esegui(null));
    }
}