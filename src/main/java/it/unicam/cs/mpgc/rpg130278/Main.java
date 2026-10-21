package it.unicam.cs.mpgc.rpg130278;

import it.unicam.cs.mpgc.rpg130278.controller.GestorePartita;
import it.unicam.cs.mpgc.rpg130278.model.Giocatore;
import it.unicam.cs.mpgc.rpg130278.model.PianoDellaTorre;
import it.unicam.cs.mpgc.rpg130278.model.Spada;
import it.unicam.cs.mpgc.rpg130278.model.eventi.RisultatoEvento;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   BENVENUTO NE LA TORRE DELL'ALCHIMISTA   ");
        System.out.println("==========================================");

        // Inizializzazione del gioco
        Spada spadaIniziale = new Spada(10);
        Giocatore giocatore = new Giocatore(spadaIniziale);
        GestorePartita gestore = new GestorePartita(giocatore);

        System.out.println("Sei pronto a scalare la torre! Sei armato con una spada da " + spadaIniziale.getDanno() + " danni.\n");

        // Game Loop principale
        while (!gestore.isGameOver()) {
            PianoDellaTorre piano = gestore.getPianoAttuale();

            System.out.println("------------------------------------------");
            System.out.println("PIANO ATTUALE: " + piano.getNumeroPiano());
            System.out.println("Punti Vita: " + giocatore.getPuntiVita() + " | Danno Spada: " + giocatore.getSpada().getDanno());
            System.out.println("Davanti a te ci sono 3 porte (0, 1, 2).");
            System.out.print("Scegli quale porta aprire: ");

            int scelta = -1;
            if (scanner.hasNextInt()) {
                scelta = scanner.nextInt();
            } else {
                scanner.next(); // Pulisce l'input non valido
            }

            if (scelta < 0 || scelta >= PianoDellaTorre.NUMERO_PORTE) {
                System.out.println("Scelta non valida! Inserisci 0, 1 o 2.\n");
                continue;
            }

            // Esecuzione della scelta
            RisultatoEvento risultato = gestore.scegliPorta(scelta);
            System.out.println("\n-> " + risultato.messaggio());

            if (giocatore.isMorto()) {
                System.out.println("\n==========================================");
                System.out.println("               GAME OVER                  ");
                System.out.println("Sei caduto al Piano " + piano.getNumeroPiano() + ".");
                System.out.println("==========================================");
            } else {
                System.out.println("Avanzi al prossimo livello!\n");
            }
        }

        scanner.close();
    }
}