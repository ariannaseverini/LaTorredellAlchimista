package it.unicam.cs.mpgc.rpg130278.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MostroTest {

    @Test
    void creazioneMostroValido() {
        Mostro mostro = new Mostro("Goblin", 30, 8);
        assertEquals("Goblin", mostro.getNome());
        assertEquals(30, mostro.getPuntiVita());
        assertEquals(8, mostro.getDanno());
        assertFalse(mostro.isMorto());
    }

    @Test
    void nomeNullOVuotoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new Mostro(null, 30, 8));
        assertThrows(IllegalArgumentException.class, () -> new Mostro("", 30, 8));
        assertThrows(IllegalArgumentException.class, () -> new Mostro("   ", 30, 8));
    }

    @Test
    void puntiVitaODannoInvalidiLancianoEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new Mostro("Orc", 0, 8));
        assertThrows(IllegalArgumentException.class, () -> new Mostro("Orc", -10, 8));
        assertThrows(IllegalArgumentException.class, () -> new Mostro("Orc", 30, 0));
        assertThrows(IllegalArgumentException.class, () -> new Mostro("Orc", 30, -5));
    }

    @Test
    void subisciDannoRiducePuntiVita() {
        Mostro mostro = new Mostro("Orc", 50, 10);
        mostro.subisciDanno(20);
        assertEquals(30, mostro.getPuntiVita());
    }

    @Test
    void subisciDannoNonScendeSottoZeroEMarcaComeMorto() {
        Mostro mostro = new Mostro("Orc", 50, 10);
        mostro.subisciDanno(60);
        assertEquals(0, mostro.getPuntiVita());
        assertTrue(mostro.isMorto());
    }

    @Test
    void subisciDannoInvalidoLanciaEccezione() {
        Mostro mostro = new Mostro("Orc", 50, 10);
        assertThrows(IllegalArgumentException.class, () -> mostro.subisciDanno(0));
        assertThrows(IllegalArgumentException.class, () -> mostro.subisciDanno(-5));
    }
}