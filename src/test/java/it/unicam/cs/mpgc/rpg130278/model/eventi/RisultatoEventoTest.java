package it.unicam.cs.mpgc.rpg130278.model.eventi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RisultatoEventoTest {

    @Test
    void valoriValidiVengonoMemorizzati() {
        RisultatoEvento risultato = new RisultatoEvento(true, "Messaggio", 2);

        assertTrue(risultato.successo());
        assertEquals("Messaggio", risultato.messaggio());
        assertEquals(2, risultato.pianiIndietro());
    }

    @Test
    void costruttoreADueArgomentiImpostaPianiIndietroAZero() {
        assertEquals(0, new RisultatoEvento(true, "Messaggio").pianiIndietro());
    }

    @Test
    void messaggioNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new RisultatoEvento(true, null));
    }

    @Test
    void messaggioVuotoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new RisultatoEvento(true, "  "));
    }

    @Test
    void pianiIndietroNegativiLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new RisultatoEvento(true, "Messaggio", -1));
    }
}