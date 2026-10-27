package it.unicam.cs.mpgc.rpg130278.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CombattimentoTest {

    private Giocatore giocatore;
    private Mostro mostro;
    private Combattimento combattimento;

    @BeforeEach
    void setUp() {
        giocatore = new Giocatore(new Spada(10));
        mostro = new Mostro("Goblin", 50, 8);
        combattimento = new Combattimento(giocatore, mostro);
    }

    @Test
    void costruttoreConGiocatoreNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new Combattimento(null, mostro));
    }

    @Test
    void costruttoreConMostroNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new Combattimento(giocatore, null));
    }

    @Test
    void scontroAppenaCreatoNonEFinitoNeVinto() {
        assertFalse(combattimento.isFinito());
        assertFalse(combattimento.isVinto());
        assertFalse(combattimento.isAttaccoPotenziato());
    }

    @Test
    void attaccareTogliePuntiVitaAlMostroPariAlDannoDellaSpada() {
        combattimento.eseguiTurno(Azione.ATTACCA);

        assertEquals(40, mostro.getPuntiVita());
    }

    @Test
    void dopoUnAttaccoIlMostroVivoContrattacca() {
        combattimento.eseguiTurno(Azione.ATTACCA);

        assertEquals(92, giocatore.getPuntiVita());
    }

    @Test
    void ilMostroMortoNonContrattacca() {
        mostro = new Mostro("Debole", 10, 8);
        combattimento = new Combattimento(giocatore, mostro);

        combattimento.eseguiTurno(Azione.ATTACCA);

        assertTrue(mostro.isMorto());
        assertEquals(100, giocatore.getPuntiVita());
    }

    @Test
    void difendersiNonColpisceIlMostroEDimezzaIlDannoSubito() {
        combattimento.eseguiTurno(Azione.DIFENDI);

        assertEquals(50, mostro.getPuntiVita());
        assertEquals(96, giocatore.getPuntiVita());
        assertTrue(combattimento.isAttaccoPotenziato());
    }

    @Test
    void ilDannoDimezzatoNonScendeMaiSottoUno() {
        mostro = new Mostro("Topo", 50, 1);
        combattimento = new Combattimento(giocatore, mostro);

        combattimento.eseguiTurno(Azione.DIFENDI);

        assertEquals(99, giocatore.getPuntiVita());
    }

    @Test
    void difendersiPoiAttaccareInfliggeDannoDoppio() {
        combattimento.eseguiTurno(Azione.DIFENDI);
        combattimento.eseguiTurno(Azione.ATTACCA);

        assertEquals(30, mostro.getPuntiVita());
        assertFalse(combattimento.isAttaccoPotenziato());
    }

    @Test
    void ilSecondoAttaccoDopoLaDifesaTornaNormale() {
        combattimento.eseguiTurno(Azione.DIFENDI);
        combattimento.eseguiTurno(Azione.ATTACCA);
        combattimento.eseguiTurno(Azione.ATTACCA);

        assertEquals(20, mostro.getPuntiVita());
    }

    @Test
    void sconfiggereIlMostroTerminaLoScontroConVittoria() {
        mostro = new Mostro("Debole", 10, 8);
        combattimento = new Combattimento(giocatore, mostro);

        combattimento.eseguiTurno(Azione.ATTACCA);

        assertTrue(combattimento.isFinito());
        assertTrue(combattimento.isVinto());
    }

    @Test
    void morireTerminaLoScontroSenzaVittoria() {
        mostro = new Mostro("Drago", 1000, 500);
        combattimento = new Combattimento(giocatore, mostro);

        combattimento.eseguiTurno(Azione.ATTACCA);

        assertTrue(giocatore.isMorto());
        assertTrue(combattimento.isFinito());
        assertFalse(combattimento.isVinto());
    }

    @Test
    void eseguireUnTurnoASconrtoFinitoLanciaEccezione() {
        mostro = new Mostro("Debole", 10, 8);
        combattimento = new Combattimento(giocatore, mostro);
        combattimento.eseguiTurno(Azione.ATTACCA);

        assertThrows(IllegalStateException.class,
                () -> combattimento.eseguiTurno(Azione.ATTACCA));
    }

    @Test
    void eseguireUnTurnoConAzioneNullaLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> combattimento.eseguiTurno(null));
    }

    @Test
    void ilTurnoRestituisceUnRaccontoCheNominaIlMostro() {
        String racconto = combattimento.eseguiTurno(Azione.ATTACCA);

        assertTrue(racconto.contains("Goblin"));
    }
}