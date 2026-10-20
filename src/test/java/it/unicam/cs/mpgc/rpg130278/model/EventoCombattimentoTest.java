package it.unicam.cs.mpgc.rpg130278.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventoCombattimentoTest {

    @Test
    void giocatoreVinceCombattimento() {
        Spada spadaForte = new Spada(20);
        Giocatore giocatore = new Giocatore(spadaForte);
        Mostro goblin = new Mostro("Goblin", 30, 10);
        EventoCombattimento evento = new EventoCombattimento(goblin);

        RisultatoEvento risultato = evento.esegui(giocatore);

        assertTrue(risultato.successo());
        assertTrue(goblin.isMorto());
        assertFalse(giocatore.isMorto());
        assertEquals(90, giocatore.getPuntiVita()); // 1 colpo subito da 10 danni
    }

    @Test
    void giocatorePerdeCombattimento() {
        Giocatore giocatore = new Giocatore( new Spada( 10));
        Mostro drago = new Mostro("Drago", 200, 50);
        EventoCombattimento evento = new EventoCombattimento(drago);

        RisultatoEvento risultato = evento.esegui(giocatore);

        assertFalse(risultato.successo());
        assertTrue(giocatore.isMorto());
    }

    @Test
    void inputNullLanciaEccezione() {
        Mostro goblin = new Mostro("Goblin", 30, 10);
        assertThrows(IllegalArgumentException.class, () -> new EventoCombattimento(null));

        EventoCombattimento evento = new EventoCombattimento(goblin);
        assertThrows(IllegalArgumentException.class, () -> evento.esegui(null));
    }
}