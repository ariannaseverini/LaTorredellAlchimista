package it.unicam.cs.mpgc.rpg130278.controller;

import it.unicam.cs.mpgc.rpg130278.model.*;
import it.unicam.cs.mpgc.rpg130278.model.eventi.*;
import it.unicam.cs.mpgc.rpg130278.persistenza.ArchivioPartite;
import it.unicam.cs.mpgc.rpg130278.persistenza.SalvataggioPartita;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServizioSalvataggioTest {

    // Un archivio finto che tiene il salvataggio in memoria, senza scrivere file
    private static class ArchivioInMemoria implements ArchivioPartite {
        private SalvataggioPartita contenuto;

        @Override
        public void salva(SalvataggioPartita salvataggio) {
            contenuto = salvataggio;
        }

        @Override
        public Optional<SalvataggioPartita> carica() {
            return Optional.ofNullable(contenuto);
        }

        @Override
        public void elimina() {
            contenuto = null;
        }
    }

    private ArchivioInMemoria archivio;
    private ServizioSalvataggio servizio;

    @BeforeEach
    void setUp() {
        archivio = new ArchivioInMemoria();
        servizio = new ServizioSalvataggio(archivio);
    }

    private GestorePartita partitaConMostroInvincibile() {
        Evento mostro = new EventoCombattimento(new Mostro("Drago", 1000, 500));
        Porta[] porte = {new Porta(mostro), new Porta(mostro), new Porta(mostro)};
        GestorePartita partita = new GestorePartita(new Giocatore(new Spada(10)));
        partita.setPianoAttuale(new PianoDellaTorre(1, porte));
        return partita;
    }

    @Test
    void costruttoreConArchivioNulloLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> new ServizioSalvataggio(null));
    }

    @Test
    void salvareUnaPartitaNullaLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> servizio.salva(null));
    }

    @Test
    void riprendereUnaPartitaSalvataRestituisceGliStessiDati() {
        GestorePartita partita = new GestorePartita(new Giocatore(new Spada(17), 63), 4);
        servizio.salva(partita);

        GestorePartita ripresa = servizio.riprendi().orElseThrow();

        assertEquals(4, ripresa.getPianoAttuale().getNumeroPiano());
        assertEquals(63, ripresa.getGiocatore().getPuntiVita());
        assertEquals(17, ripresa.getGiocatore().getSpada().getDanno());
        assertEquals(StatoPartita.IN_CORSO, ripresa.getStato());
    }

    @Test
    void riprendereSenzaSalvataggioRestituisceOptionalVuoto() {
        assertTrue(servizio.riprendi().isEmpty());
    }

    @Test
    void salvareDuranteUnCombattimentoLanciaEccezione() {
        GestorePartita partita = partitaConMostroInvincibile();
        partita.scegliPorta(0);

        assertThrows(IllegalStateException.class, () -> servizio.salva(partita));
    }

    @Test
    void salvareAPartitaPersaLanciaEccezione() {
        GestorePartita partita = partitaConMostroInvincibile();
        partita.scegliPorta(0);
        partita.eseguiTurno(Azione.ATTACCA);

        assertThrows(IllegalStateException.class, () -> servizio.salva(partita));
    }

    @Test
    void eliminareRimuoveIlSalvataggio() {
        servizio.salva(new GestorePartita(new Giocatore(new Spada(10)), 3));

        servizio.elimina();

        assertTrue(servizio.riprendi().isEmpty());
    }

    @Test
    void riprendereUnSalvataggioConPianoOltreLUltimoRestituisceOptionalVuoto() {
        archivio.salva(new SalvataggioPartita(99, 50, 10));

        assertTrue(servizio.riprendi().isEmpty());
    }

    @Test
    void riprendereUnSalvataggioConTroppiPuntiVitaRestituisceOptionalVuoto() {
        archivio.salva(new SalvataggioPartita(3, 101, 10));

        assertTrue(servizio.riprendi().isEmpty());
    }
}