package it.unicam.cs.mpgc.rpg130278.model.eventi;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RisultatoEventoTest {

    @Test
    void valoriValidiVengonoMemorizzati() {
        RisultatoEvento risultato = new RisultatoEvento(true, "Messaggio", 2, TipoEvento.TRAPPOLA);

        assertTrue(risultato.successo());
        assertEquals("Messaggio", risultato.messaggio());
        assertEquals(2, risultato.pianiIndietro());
        assertEquals(TipoEvento.TRAPPOLA, risultato.tipo());
    }

    @Test
    void costruttoreATreArgomentiImpostaPianiIndietroAZero() {
        RisultatoEvento risultato = new RisultatoEvento(true, "Messaggio", TipoEvento.FORZIERE);

        assertEquals(0, risultato.pianiIndietro());
        assertEquals(TipoEvento.FORZIERE, risultato.tipo());
    }

    @Test
    void messaggioNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class,
                () -> new RisultatoEvento(true, null, TipoEvento.FORZIERE));
    }

    @Test
    void messaggioVuotoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class,
                () -> new RisultatoEvento(true, "  ", TipoEvento.FORZIERE));
    }

    @Test
    void pianiIndietroNegativiLanciaEccezione() {
        assertThrows(IllegalArgumentException.class,
                () -> new RisultatoEvento(true, "Messaggio", -1, TipoEvento.TRAPPOLA));
    }

    @Test
    void tipoNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class,
                () -> new RisultatoEvento(true, "Messaggio", null));
    }
}