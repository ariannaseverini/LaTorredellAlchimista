package it.unicam.cs.mpgc.rpg130278.model;

import it.unicam.cs.mpgc.rpg130278.model.eventi.Evento;
import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoCombattimento;
import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoForziere;
import it.unicam.cs.mpgc.rpg130278.model.eventi.EventoTrappola;

import java.util.Random;

public class PianoDellaTorre {
    public static final int NUMERO_PORTE = 3;
    private final int numeroPiano;
    private final Porta[] porte;

    public PianoDellaTorre(int numeroPiano) {
        this(numeroPiano, generaPorteCasuali(new Random(), numeroPiano));
    }

    public PianoDellaTorre(int numeroPiano, Porta[] porte) {
        if (numeroPiano <= 0) throw new IllegalArgumentException();
        if (porte == null || porte.length != NUMERO_PORTE) throw new IllegalArgumentException();
        for (int i = 0; i < porte.length; i++) {
            if (porte[i] == null) throw new IllegalArgumentException();
        }
        this.numeroPiano = numeroPiano;
        this.porte = porte.clone();
    }

    public int getNumeroPiano() {
        return numeroPiano;
    }

    public Porta getPorta(int indice) {
        if (indice < 0 || indice >= porte.length) throw new IllegalArgumentException("Indice non valido");
        return porte[indice];
    }

    public Porta[] getPorte() {
        return porte.clone();
    }

    private static Porta[] generaPorteCasuali(Random random, int numeroPiano) {
        Porta[] nuovePorte = new Porta[NUMERO_PORTE];
        for (int i = 0; i < NUMERO_PORTE; i++) {
            nuovePorte[i] = new Porta(generaEventoCasuale(random, numeroPiano));
        }
        return nuovePorte;
    }

    private static Evento generaEventoCasuale(Random random, int numeroPiano) {
        int tipo = random.nextInt(3);
        return switch (tipo) {
            case 0 -> new EventoCombattimento(generaMostroCasuale(random, numeroPiano));
            case 1 -> new EventoTrappola(1 + random.nextInt(3)); // scende di 1-3 piani
            case 2 -> new EventoForziere(new Spada(15 + random.nextInt(10))); // Spada da 15 a 24
            default -> throw new IllegalStateException("Tipo di evento non valido");
        };

    }

    private static Mostro generaMostroCasuale(Random random, int numeroPiano) {
        String[] nomiMostri = {"Goblin", "Scheletro", "Orso", "Spettro"};
        String nome = nomiMostri[random.nextInt(nomiMostri.length)];

        //man mano che il numero di piano sale i mostri diventano piu forti e cambiano ad ogni generazione
        int puntiVita = 20 + (numeroPiano * 5) + random.nextInt(10);
        int danno = 5 + (numeroPiano * 2) + random.nextInt(5);

        return new Mostro(nome, puntiVita, danno);
    }
}