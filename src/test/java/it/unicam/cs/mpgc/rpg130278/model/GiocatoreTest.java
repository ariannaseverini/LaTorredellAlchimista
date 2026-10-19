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
}
