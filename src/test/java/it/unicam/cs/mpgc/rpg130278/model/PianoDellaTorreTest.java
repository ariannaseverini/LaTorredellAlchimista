package it.unicam.cs.mpgc.rpg130278.model;

import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoCombattimento;
import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoForziere;
import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoTrappola;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PianoDellaTorreTest {

    @Test
    void creazionePianoStandardGeneraTrePorte() {
        PianoDellaTorre piano = new PianoDellaTorre(1);

        assertEquals(1, piano.getNumeroPiano());
        assertEquals(PianoDellaTorre.NUMERO_PORTE, piano.getPorte().length);
        assertNotNull(piano.getPorta(0));
        assertNotNull(piano.getPorta(1));
        assertNotNull(piano.getPorta(2));
    }

    @Test
    void creazionePianoConPorteCustom() {
        Porta p0 = new Porta(new EventoTrappola(10));
        Porta p1 = new Porta(new EventoForziere(5));
        Porta p2 = new Porta(new EventoCombattimento(new Mostro("Goblin", 30, 10)));

        Porta[] porteCustom = new Porta[]{p0, p1, p2};
        PianoDellaTorre piano = new PianoDellaTorre(3, porteCustom);

        assertEquals(3, piano.getNumeroPiano());
        assertSame(p0, piano.getPorta(0));
        assertSame(p1, piano.getPorta(1));
        assertSame(p2, piano.getPorta(2));
    }

    @Test
    void numeroPianoZeroONegativoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new PianoDellaTorre(0));
        assertThrows(IllegalArgumentException.class, () -> new PianoDellaTorre(-1));
    }

    @Test
    void arrayPorteNonValidoLanciaEccezione() {
        Porta p = new Porta(new EventoTrappola(10));

        // Array null
        assertThrows(IllegalArgumentException.class, () -> new PianoDellaTorre(1, null));
        // Meno di 3 porte (2 porte)
        assertThrows(IllegalArgumentException.class, () -> new PianoDellaTorre(1, new Porta[]{p, p}));
        // Più di 3 porte (4 porte)
        assertThrows(IllegalArgumentException.class, () -> new PianoDellaTorre(1, new Porta[]{p, p, p, p}));
        // Contiene un elemento null
        assertThrows(IllegalArgumentException.class, () -> new PianoDellaTorre(1, new Porta[]{p, p, null}));
    }

    @Test
    void getPortaIndiceNonValidoLanciaEccezione() {
        PianoDellaTorre piano = new PianoDellaTorre(1);

        assertThrows(IllegalArgumentException.class, () -> piano.getPorta(-1));
        assertThrows(IllegalArgumentException.class, () -> piano.getPorta(3));
    }

    @Test
    void modificaArrayEsternoNonInfluenzaPiano() {
        PianoDellaTorre piano = new PianoDellaTorre(1);
        Porta portaOriginale = piano.getPorta(0);

        Porta[] porteCopia = piano.getPorte();
        porteCopia[0] = new Porta(new EventoTrappola(99));

        assertSame(portaOriginale, piano.getPorta(0));
    }
}