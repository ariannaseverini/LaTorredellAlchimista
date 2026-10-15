package it.unicam.cs.mpgc.rpg130278.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SpadaTest {
    private Spada spada;

    @BeforeEach
    void setUp(){
        spada = new Spada(10);
    }

    @Test
    void spadaCreata(){
        assertEquals(10, spada.getDanno());
    }

    @Test
    void valoreDannoNegativo(){
        assertThrows(IllegalArgumentException.class, () -> new Spada(-5));
    }

    @Test
    void valoreDannoZero(){
        assertThrows(IllegalArgumentException.class, () -> new Spada(0));

    }

}