package it.unicam.cs.mpgc.rpg130278.controller;

import it.unicam.cs.mpgc.rpg130278.model.*;
import it.unicam.cs.mpgc.rpg130278.model.eventi.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GestorePartitaTest {

    private Spada spadaIniziale;
    private Giocatore giocatore;
    private GestorePartita gestore;

    @BeforeEach
    void setUp() {
        spadaIniziale = new Spada(10);
        giocatore = new Giocatore(spadaIniziale);
        gestore = new GestorePartita(giocatore);
    }

    private PianoDellaTorre pianoConTrePorteUguali(int numeroPiano, Evento evento) {
        Porta[] porte = {new Porta(evento), new Porta(evento), new Porta(evento)};
        return new PianoDellaTorre(numeroPiano, porte);
    }

    // Un mostro che il giocatore non può battere: fa morire il giocatore al primo turno
    private Evento mostroInvincibile() {
        return new EventoCombattimento(new Mostro("Drago", 1000, 500));
    }

    // Un mostro che la spada iniziale (danno 10) sconfigge con un solo attacco
    private Evento mostroDebole() {
        return new EventoCombattimento(new Mostro("Topo", 5, 1));
    }

    @Test
    void partitaAppenaCreataParteDalPrimoPianoInCorso() {
        assertSame(giocatore, gestore.getGiocatore());
        assertNotNull(gestore.getPianoAttuale());
        assertEquals(1, gestore.getPianoAttuale().getNumeroPiano());
        assertEquals(StatoPartita.IN_CORSO, gestore.getStato());
        assertFalse(gestore.isGameOver());
    }

    @Test
    void eventoInnocuoFaAvanzareDiPianoEPartitaResteInCorso() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(1, new EventoForziere(5)));

        RisultatoEvento risultato = gestore.scegliPorta(0);

        assertTrue(risultato.successo());
        assertEquals(2, gestore.getPianoAttuale().getNumeroPiano());
        assertEquals(StatoPartita.IN_CORSO, gestore.getStato());
    }

    @Test
    void trappolaFaScendereDelNumeroDiPianiIndicati() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(5, new EventoTrappola(2)));

        gestore.scegliPorta(0);

        assertEquals(3, gestore.getPianoAttuale().getNumeroPiano());
        assertEquals(StatoPartita.IN_CORSO, gestore.getStato());
    }

    @Test
    void trappolaAlPrimoPianoLasciaAlPrimoPiano() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(1, new EventoTrappola(3)));

        gestore.scegliPorta(0);

        assertEquals(1, gestore.getPianoAttuale().getNumeroPiano());
    }

    @Test
    void trappolaNonTogliePuntiVita() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(5, new EventoTrappola(2)));

        gestore.scegliPorta(0);

        assertEquals(100, giocatore.getPuntiVita());
    }

    @Test
    void aprirePortaConMostroAvviaIlCombattimentoSenzaCambiarePiano() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(3, mostroDebole()));

        gestore.scegliPorta(0);

        assertEquals(StatoPartita.IN_COMBATTIMENTO, gestore.getStato());
        assertNotNull(gestore.getCombattimentoInCorso());
        assertEquals(3, gestore.getPianoAttuale().getNumeroPiano());
        assertFalse(gestore.isGameOver());
    }

    @Test
    void scegliPortaDuranteIlCombattimentoLanciaEccezione() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(3, mostroDebole()));
        gestore.scegliPorta(0);

        assertThrows(IllegalStateException.class, () -> gestore.scegliPorta(1));
    }

    @Test
    void eseguireUnTurnoSenzaCombattimentoLanciaEccezione() {
        assertThrows(IllegalStateException.class, () -> gestore.eseguiTurno(Azione.ATTACCA));
    }

    @Test
    void difendersiNonTerminaLoScontro() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(
                3, new EventoCombattimento(new Mostro("Goblin", 1000, 4))));
        gestore.scegliPorta(0);

        gestore.eseguiTurno(Azione.DIFENDI);

        assertEquals(StatoPartita.IN_COMBATTIMENTO, gestore.getStato());
        assertNotNull(gestore.getCombattimentoInCorso());
    }

    @Test
    void vincereLoScontroRiportaInCorsoEAvanzaDiPiano() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(3, mostroDebole()));
        gestore.scegliPorta(0);

        gestore.eseguiTurno(Azione.ATTACCA);

        assertEquals(StatoPartita.IN_CORSO, gestore.getStato());
        assertEquals(4, gestore.getPianoAttuale().getNumeroPiano());
        assertNull(gestore.getCombattimentoInCorso());
    }

    @Test
    void vincereLoScontroAlPianoDieciDaLaVittoria() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(GestorePartita.NUMERO_PIANI, mostroDebole()));
        gestore.scegliPorta(0);

        gestore.eseguiTurno(Azione.ATTACCA);

        assertEquals(StatoPartita.VITTORIA, gestore.getStato());
        assertTrue(gestore.isGameOver());
        assertEquals(GestorePartita.NUMERO_PIANI, gestore.getPianoAttuale().getNumeroPiano());
    }

    @Test
    void perdereLoScontroTerminaLaPartitaConSconfitta() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(1, mostroInvincibile()));
        gestore.scegliPorta(0);

        gestore.eseguiTurno(Azione.ATTACCA);

        assertTrue(giocatore.isMorto());
        assertEquals(StatoPartita.SCONFITTA, gestore.getStato());
        assertTrue(gestore.isGameOver());
    }

    @Test
    void morireAlPianoDieciNonDaLaVittoria() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(GestorePartita.NUMERO_PIANI, mostroInvincibile()));
        gestore.scegliPorta(0);

        gestore.eseguiTurno(Azione.ATTACCA);

        assertEquals(StatoPartita.SCONFITTA, gestore.getStato());
    }

    @Test
    void trappolaAlPianoDieciNonDaLaVittoria() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(GestorePartita.NUMERO_PIANI, new EventoTrappola(1)));

        gestore.scegliPorta(0);

        assertEquals(StatoPartita.IN_CORSO, gestore.getStato());
        assertEquals(GestorePartita.NUMERO_PIANI - 1, gestore.getPianoAttuale().getNumeroPiano());
    }

    @Test
    void scegliPortaAPartitaFinitaLanciaEccezione() {
        gestore.setPianoAttuale(pianoConTrePorteUguali(1, mostroInvincibile()));
        gestore.scegliPorta(0);
        gestore.eseguiTurno(Azione.ATTACCA);

        assertThrows(IllegalStateException.class, () -> gestore.scegliPorta(0));
    }

    @Test
    void costruttoreConGiocatoreNullLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new GestorePartita(null));
    }

    @Test
    void scegliPortaConIndiceNonValidoLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> gestore.scegliPorta(-1));
        assertThrows(IllegalArgumentException.class, () -> gestore.scegliPorta(3));
    }
}