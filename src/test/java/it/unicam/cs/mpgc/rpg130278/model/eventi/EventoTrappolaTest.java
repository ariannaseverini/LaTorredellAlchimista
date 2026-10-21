package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventoTrappolaTest {

    @Test
    void trappolaRiducePuntiVitaGiocatore() {
        Giocatore giocatore = new Giocatore(new Spada(10));
        EventoTrappola trappola = new EventoTrappola(30);

        RisultatoEvento risultato = trappola.esegui(giocatore);

        assertTrue(risultato.successo());
        assertEquals(70, giocatore.getPuntiVita());
    }

    @Test
    void trappolaUccideGiocatore() {
        Giocatore giocatore = new Giocatore(new Spada(10));
        EventoTrappola trappolaMortale = new EventoTrappola(100);

        RisultatoEvento risultato = trappolaMortale.esegui(giocatore);

        assertFalse(risultato.successo());
        assertTrue(giocatore.isMorto());
    }

    @Test
    void validazioneInputNonValidi() {
        assertThrows(IllegalArgumentException.class, () -> new EventoTrappola(0));
        assertThrows(IllegalArgumentException.class, () -> new EventoTrappola(-10));

        EventoTrappola trappola = new EventoTrappola(20);
        assertThrows(IllegalArgumentException.class, () -> trappola.esegui(null));
    }
}