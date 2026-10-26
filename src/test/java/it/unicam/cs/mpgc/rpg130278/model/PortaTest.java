package it.unicam.cs.mpgc.rpg130278.model;

import it.unicam.cs.mpgc.rpg130278.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoTrappola;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PortaTest {

    @Test
    void apriPortaEsegueEvento() {
        Giocatore giocatore = new Giocatore(new Spada(10));
        Evento trappola = new EventoTrappola(2);
        Porta porta = new Porta(trappola);

        RisultatoEvento risultato = porta.apri(giocatore);

        assertTrue(risultato.successo());
        assertEquals(2, risultato.pianiIndietro());
        assertEquals(100, giocatore.getPuntiVita());
        assertSame(trappola, porta.getEvento());
    }

    @Test
    void inputNullLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new Porta(null));

        Porta porta = new Porta(new EventoTrappola(10));
        assertThrows(IllegalArgumentException.class, () -> porta.apri(null));
    }
}