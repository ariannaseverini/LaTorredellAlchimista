package it.unicam.cs.mpgc.rpg130278.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GiocatoreTest {
    private Giocatore giocatore;
    private Spada spada;

    @BeforeEach
    void setUp(){
        spada = new Spada(10);
        giocatore = new Giocatore(spada);
    }

    @Test
    void giocatoreCreato(){
        assertEquals(100, giocatore.getPuntiVita());
    }

    @Test
    void spadaNulla(){
        assertThrows(IllegalArgumentException.class, () -> new Giocatore(null));
    }

    @Test
    void getSpadaRestituisceLaSpadaDelCostruttore(){
        assertSame(spada, giocatore.getSpada());
    }

    @Test
    void dannoDi30LasciaPuntiVita70(){
        giocatore.subisciDanno(30);
        assertEquals(70, giocatore.getPuntiVita());
    }

    @Test
    void dannoNegativoLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> giocatore.subisciDanno(-10));
    }

    @Test
    void dannoZeroLanciaEccezione(){
        assertThrows(IllegalArgumentException.class, () -> giocatore.subisciDanno(0));
    }

    @Test
    void dannoUgualeAiPuntiVitaPortaAZero(){
        giocatore.subisciDanno(100);
        assertEquals(0, giocatore.getPuntiVita());
    }


    @Test
    void dannoMaggioreDeiPuntiVitaNonScendeSottoZero(){
        giocatore.subisciDanno(150);
        assertEquals(0, giocatore.getPuntiVita());
    }

    @Test
    void giocatoreAppenaCreatoNonEMorto(){
        assertFalse(giocatore.isMorto());
    }

    @Test
    void giocatoreSenzaPuntiVitaEMorto(){
        giocatore.subisciDanno(100);
        assertTrue(giocatore.isMorto());
    }

    @Test
    void equipaggiaSpadaValidaSostituisceVecchiaSpada() {
        Spada nuovaSpada = new Spada(25);
        giocatore.equipaggiaSpada(nuovaSpada);
        assertSame(nuovaSpada, giocatore.getSpada());
    }

    @Test
    void equipaggiaSpadaNullaLanciaEccezione() {
        assertThrows(IllegalArgumentException.class, () -> giocatore.equipaggiaSpada(null));
    }


}
