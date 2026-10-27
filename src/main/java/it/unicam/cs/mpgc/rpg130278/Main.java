package it.unicam.cs.mpgc.rpg130278;

import it.unicam.cs.mpgc.rpg130278.controller.GestorePartita;
import it.unicam.cs.mpgc.rpg130278.controller.StatoPartita;
import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.PianoDellaTorre;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;
import it.unicam.cs.mpgc.rpg130278.model.Azione;
import it.unicam.cs.mpgc.rpg130278.model.Combattimento;
import it.unicam.cs.mpgc.rpg130278.model.Mostro;
import java.util.Scanner;

public class Main {

    private static final int DANNO_SPADA_INIZIALE = 10;
    private static final String SEPARATORE = "==========================================";
    private static final String LINEA = "------------------------------------------";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        stampaBenvenuto();
        do {
            gioca(scanner);
        } while (vuoleRigiocare(scanner));

        System.out.println("\nGrazie per aver giocato. A presto!");
        scanner.close();
    }

    private static void gioca(Scanner scanner) {
        Giocatore giocatore = new Giocatore(new Spada(DANNO_SPADA_INIZIALE));
        GestorePartita gestore = new GestorePartita(giocatore);

        System.out.println("\nSei armato con una spada da " + DANNO_SPADA_INIZIALE + " danni. Buona fortuna!\n");

        while (!gestore.isGameOver()) {
            if (gestore.getStato() == StatoPartita.IN_COMBATTIMENTO) {
                turnoDiCombattimento(gestore, scanner);
            } else {
                turnoDiEsplorazione(gestore, scanner);
            }
        }
        stampaFinale(gestore);
    }

    private static void turnoDiEsplorazione(GestorePartita gestore, Scanner scanner) {
        stampaSituazione(gestore);
        int indicePorta = leggiPorta(scanner);
        RisultatoEvento risultato = gestore.scegliPorta(indicePorta);
        System.out.println("\n" + risultato.messaggio() + "\n");
    }

    private static void turnoDiCombattimento(GestorePartita gestore, Scanner scanner) {
        Combattimento combattimento = gestore.getCombattimentoInCorso();
        Giocatore giocatore = gestore.getGiocatore();
        Mostro mostro = combattimento.getMostro();

        System.out.println(LINEA);
        System.out.println("COMBATTIMENTO contro " + mostro.getNome());
        System.out.println("I tuoi HP: " + giocatore.getPuntiVita()
                + " | HP " + mostro.getNome() + ": " + mostro.getPuntiVita());
        if (combattimento.isAttaccoPotenziato()) {
            System.out.println("Il tuo prossimo attacco farà il doppio dei danni!");
        }

        Azione azione = leggiAzione(scanner);
        System.out.println("\n" + gestore.eseguiTurno(azione) + "\n");

        if (combattimento.isVinto()) {
            System.out.println("Hai sconfitto " + mostro.getNome() + "!");
            if (!gestore.isGameOver()) {
                System.out.println("Sali al piano successivo...\n");
            }
        }
    }

    private static Azione leggiAzione(Scanner scanner) {
        while (true) {
            System.out.print("Cosa fai? [A] attacca  [D] difendi: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("a")) return Azione.ATTACCA;
            if (input.equals("d")) return Azione.DIFENDI;
            System.out.println("Comando non valido: scrivi A oppure D.");
        }
    }
    private static void stampaBenvenuto() {
        System.out.println(SEPARATORE);
        System.out.println("     BENVENUTO NELLA TORRE DELL'ALCHIMISTA");
        System.out.println(SEPARATORE);
        System.out.println("Scala la torre di " + GestorePartita.NUMERO_PIANI + " piani.");
        System.out.println("A ogni piano scegli una porta: dietro puoi trovare");
        System.out.println("un mostro, un forziere o una trappola.");
    }

    private static void stampaSituazione(GestorePartita gestore) {
        Giocatore giocatore = gestore.getGiocatore();
        System.out.println(LINEA);
        System.out.println("PIANO " + gestore.getPianoAttuale().getNumeroPiano()
                + " DI " + GestorePartita.NUMERO_PIANI);
        System.out.println("Punti vita: " + giocatore.getPuntiVita()
                + " | Danno spada: " + giocatore.getSpada().getDanno());
        System.out.println("Davanti a te ci sono " + PianoDellaTorre.NUMERO_PORTE
                + " porte, numerate da 1 a " + PianoDellaTorre.NUMERO_PORTE + ".");
    }

    private static int leggiPorta(Scanner scanner) {
        while (true) {
            System.out.print("Quale porta apri? ");
            String input = scanner.nextLine().trim();

            if (input.matches("\\d{1,2}")) {
                int numero = Integer.parseInt(input);
                if (numero >= 1 && numero <= PianoDellaTorre.NUMERO_PORTE) {
                    return numero - 1;
                }
            }
            System.out.println("Scelta non valida: scrivi un numero da 1 a "
                    + PianoDellaTorre.NUMERO_PORTE + ".");
        }
    }

    private static void stampaFinale(GestorePartita gestore) {
        System.out.println("\n" + SEPARATORE);
        if (gestore.getStato() == StatoPartita.VITTORIA) {
            System.out.println("   HAI VINTO! Hai scalato tutta la torre!");
        } else {
            System.out.println("                GAME OVER");
            System.out.println("   Sei caduto al piano " + gestore.getPianoAttuale().getNumeroPiano() + ".");
        }
        System.out.println(SEPARATORE);
    }

    private static boolean vuoleRigiocare(Scanner scanner) {
        while (true) {
            System.out.print("\nVuoi giocare ancora? (s/n) ");
            String risposta = scanner.nextLine().trim().toLowerCase();

            if (risposta.equals("s")) return true;
            if (risposta.equals("n")) return false;
            System.out.println("Rispondi con 's' o 'n'.");
        }
    }
}