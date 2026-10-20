package it.unicam.cs.mpgc.rpg130278.model.eventi;

import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.RisultatoEvento;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventoForziereTest {

    @Test
    void forziereEquipaggiaSpadaSeMigliore() {
        Giocatore giocatore = new Giocatore(new Spada(10));
        EventoForziere forziere = new EventoForziere(new Spada(25));

        RisultatoEvento risultato = forziere.esegui(giocatore);

        assertTrue(risultato.successo());
        assertEquals(25, giocatore.getSpada().getDanno());
    }

    @Test
    void forziereNonEquipaggiaSpadaSePeggioreOPari() {
        Giocatore giocatore = new Giocatore(new Spada(20));
        EventoForziere forziere = new EventoForziere(new Spada(15));

        RisultatoEvento risultato = forziere.esegui(giocatore);

        assertTrue(risultato.successo());
        assertEquals(20, giocatore.getSpada().getDanno()); // La spada rimane quella iniziale da 20
    }

    @Test
    void inputNullLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new EventoForziere(null));

        EventoForziere forziere = new EventoForziere(new Spada(20));
        assertThrows(IllegalArgumentException.class, () -> forziere.esegui(null));
    }
}