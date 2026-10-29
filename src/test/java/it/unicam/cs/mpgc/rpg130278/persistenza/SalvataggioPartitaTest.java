package it.unicam.cs.mpgc.rpg130278.persistenza;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalvataggioPartitaTest {

    @Test
    void valoriValidiVengonoMemorizzati() {
        SalvataggioPartita salvataggio = new SalvataggioPartita(4, 63, 17);

        assertEquals(4, salvataggio.piano());
        assertEquals(63, salvataggio.puntiVita());
        assertEquals(17, salvataggio.dannoSpada());
    }

    @Test
    void pianoZeroLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new SalvataggioPartita(0, 50, 10));
    }

    @Test
    void pianoNegativoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new SalvataggioPartita(-3, 50, 10));
    }

    @Test
    void primoPianoEAccettato() {
        assertEquals(1, new SalvataggioPartita(1, 50, 10).piano());
    }

    @Test
    void puntiVitaZeroLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new SalvataggioPartita(3, 0, 10));
    }

    @Test
    void puntiVitaNegativiLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new SalvataggioPartita(3, -5, 10));
    }

    @Test
    void unSoloPuntoVitaEAccettato() {
        assertEquals(1, new SalvataggioPartita(3, 1, 10).puntiVita());
    }

    @Test
    void dannoSpadaZeroLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new SalvataggioPartita(3, 50, 0));
    }

    @Test
    void dannoSpadaNegativoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new SalvataggioPartita(3, 50, -2));
    }

    @Test
    void dueSalvataggiConGliStessiValoriSonoUguali() {
        assertEquals(new SalvataggioPartita(4, 63, 17), new SalvataggioPartita(4, 63, 17));
    }
}